package com.toolslab.cowork.app.view.map

import android.support.annotation.VisibleForTesting
import com.toolslab.cowork.BuildConfig.API_PASSWORD
import com.toolslab.cowork.BuildConfig.API_USER
import com.toolslab.cowork.app.util.max
import com.toolslab.cowork.app.util.min
import com.toolslab.cowork.base_mvp.BasePresenter
import com.toolslab.cowork.base_network.storage.Credentials
import com.toolslab.cowork.base_repository.exception.NoConnectionException
import com.toolslab.cowork.base_repository.exception.NotFoundException
import com.toolslab.cowork.base_repository.model.Space
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

internal class CoworkPresenter @Inject constructor() :
        BasePresenter<CoworkContract.View>(), CoworkContract.Presenter {

    @Inject
    internal lateinit var compositeDisposable: CompositeDisposable

    @Inject
    internal lateinit var coworkInteractor: CoworkInteractor

    override fun onBound(view: CoworkContract.View) {
        view.getMapAsync()
    }

    override fun onUnbound(view: CoworkContract.View) {
        compositeDisposable.clear()
    }

    override fun searchSpaces(country: String, city: String, space: String) {
        if (country.isEmpty()) {
            view.showInputMissesCountryError()
            return
        }
        compositeDisposable.add(coworkInteractor.listSpaces(createCredentials(), country, city, space)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { spaces ->
                            Timber.d("${spaces.size} spaces found for $country, $city, $space")
                            view.removeMarkers()
                            spaces.forEach {
                                view.addMapMarker(it)
                            }
                            moveCamera(spaces)
                        },
                        {
                            Timber.e(it)
                            when (it) {
                                is NotFoundException -> view.showNoPlacesFoundError(createLocationDescription(country, city))
                                is NoConnectionException -> view.showNoConnectionError()
                                else -> view.showDefaultError()
                            }
                        }
                )
        )
    }

    override fun onMapReady() {
        view.showSearch()
    }

    override fun onUserMapGestureStopped(country: String, city: String) {
        if (country.isNotEmpty()) {
            searchSpaces(country, city)
        }
    }

    private fun searchSpaces(country: String, city: String) {
        compositeDisposable.clear() // Avoid two request arriving at the same time
        compositeDisposable.add(coworkInteractor.listSpaces(createCredentials(), country, city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { spaces ->
                            Timber.d("${spaces.size} spaces found for $country, $city")
                            view.removeMarkers()
                            spaces.forEach {
                                view.addMapMarker(it)
                            }
                        },
                        {
                            Timber.e(it)
                            when (it) {
                                is NotFoundException -> view.showNoPlacesFoundError(createLocationDescription(country, city))
                                is NoConnectionException -> view.showNoConnectionError()
                                else -> view.showDefaultError()
                            }
                        }
                )
        )
    }

    @VisibleForTesting
    internal fun moveCamera(spaces: List<Space>) {
        // Zoom into map close enough to show all spaces
        val latitudes = spaces.map { it.latitude }
        val longitudes = spaces.map { it.longitude }
        view.moveCamera(latitudes.min(), longitudes.min(), latitudes.max(), longitudes.max())
    }

    @VisibleForTesting
    internal fun createCredentials() = Credentials(API_USER, API_PASSWORD)

    private fun createLocationDescription(country: String, city: String): String {
        return if (city.isEmpty()) country else "$city, $country"
    }

}
