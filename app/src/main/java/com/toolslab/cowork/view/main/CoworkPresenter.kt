package com.toolslab.cowork.view.main

import android.support.annotation.VisibleForTesting
import com.toolslab.cowork.BuildConfig.API_PASSWORD
import com.toolslab.cowork.BuildConfig.API_USER
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

class CoworkPresenter @Inject constructor() :
        BasePresenter<CoworkContract.View>(), CoworkContract.Presenter {

    @Inject
    internal lateinit var compositeDisposable: CompositeDisposable

    @Inject
    internal lateinit var coworkInteractor: CoworkInteractor

    @VisibleForTesting
    internal var spaces: List<Space> = emptyList()

    @VisibleForTesting
    internal var isMapReady = false

    override fun onBound(view: CoworkContract.View) {
        view.getMapAsync()
    }

    override fun onUnbound(view: CoworkContract.View) {
        compositeDisposable.clear()
    }

    override fun listSpaces(country: String, city: String, space: String) {
        if (country.isEmpty()) {
            view.showMessage("Give at least a country")
            return
        }
        compositeDisposable.add(coworkInteractor.listSpaces(createCredentials(), country, city, space)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Timber.d("${it.size} spaces found:\n $it")
                            spaces = it
                            if (isMapReady) {
                                onMapReady()
                            }
                        },
                        {
                            Timber.e(it)
                            when (it) {
                                is NotFoundException -> view.showMessage("No places found") // TODO extract strings
                                is NoConnectionException -> view.showMessage("No Internet Connection")
                                else -> view.showMessage(it.message ?: "Unknown error occurred")
                            }
                        }
                )
        )
    }

    override fun onMapReady() {
        isMapReady = true
        // Add a markers and move the camera
        if (spaces.isNotEmpty()) {
            spaces.forEach {
                view.addMapMarker(it)
            }
            view.moveCamera(spaces[0]) // TODO choose where to move to
        }
    }

    @VisibleForTesting
    internal fun createCredentials() = Credentials(API_USER, API_PASSWORD)

}
