package com.toolslab.cowork.view.main

import com.toolslab.base_mvp.BasePresenter
import com.toolslab.cowork.db.SpaceRepository
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
    internal lateinit var spaceRepository: SpaceRepository

    override fun onBound(view: CoworkContract.View) {
        view.showMessage("Ready to search!")
    }

    override fun onUnbound(view: CoworkContract.View) {
        compositeDisposable.clear()
    }

    override fun listSpaces(country: String, city: String, space: String) {
        view.showMessage("Loading")
        compositeDisposable.add(spaceRepository.listSpaces(country, city, space)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            view.showSpaces(it)
                        },
                        {
                            Timber.e(it)
                            view.showMessage(it.message)
                        }
                )
        )
    }

}
