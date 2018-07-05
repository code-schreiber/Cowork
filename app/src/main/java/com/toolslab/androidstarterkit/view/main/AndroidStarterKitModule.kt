package com.toolslab.androidstarterkit.view.main

import dagger.Module
import dagger.Provides

@Module
class AndroidStarterKitModule {

    @Provides
    fun providePresenter(): AndroidStarterKitContract.Presenter =
            AndroidStarterKitPresenter()

}
