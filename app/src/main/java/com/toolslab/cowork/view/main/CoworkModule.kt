package com.toolslab.cowork.view.main

import com.toolslab.cowork.network.CoworkingMapService
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class CoworkModule {

    @Provides
    fun providePresenter(compositeDisposable: CompositeDisposable, coworkingMapService: CoworkingMapService): CoworkContract.Presenter =
            CoworkPresenter(compositeDisposable,coworkingMapService)

}
