package com.toolslab.cowork.view.main

import com.toolslab.base_mvp.BasePresenter
import com.toolslab.cowork.network.CoworkingMapService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject


class CoworkPresenter @Inject constructor(private val compositeDisposable: CompositeDisposable,
                                          private val coworkingMapService: CoworkingMapService) :
        BasePresenter<CoworkContract.View>(), CoworkContract.Presenter {

    override fun onBound(view: CoworkContract.View) {
        compositeDisposable.add(coworkingMapService.getJwt("auser", "apwd")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Timber.d("result:$result")
                        },
                        { error ->
                            Timber.e(error.message)
                        }
                )
        )
    }

    override fun onUnbound(view: CoworkContract.View) {
        compositeDisposable.clear()
    }

}
