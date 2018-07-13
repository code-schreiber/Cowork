package com.toolslab.cowork.view.main

import com.toolslab.cowork.db.SpaceRepository
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class CoworkModule {

    @Provides
    fun providePresenter(compositeDisposable: CompositeDisposable, spaceRepository: SpaceRepository): CoworkContract.Presenter =
            CoworkPresenter(compositeDisposable, spaceRepository)

}
