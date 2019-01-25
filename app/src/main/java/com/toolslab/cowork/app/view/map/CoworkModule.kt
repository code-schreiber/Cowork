package com.toolslab.cowork.app.view.map

import dagger.Module
import dagger.Provides

@Module
class CoworkModule {

    @Provides
    internal fun providePresenter(coworkPresenter: CoworkPresenter): CoworkContract.Presenter = coworkPresenter

}
