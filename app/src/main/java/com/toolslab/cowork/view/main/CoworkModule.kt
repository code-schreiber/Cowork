package com.toolslab.cowork.view.main

import dagger.Module
import dagger.Provides

@Module
class CoworkModule {

    @Provides
    internal fun providePresenter(coworkPresenter: CoworkPresenter): CoworkContract.Presenter = coworkPresenter

}
