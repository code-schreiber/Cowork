package com.toolslab.cowork

import com.toolslab.base_repository.di.DaggerRepositoryComponent
import com.toolslab.cowork.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import timber.log.Timber.DebugTree

class Cowork : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val repositoryComponent = DaggerRepositoryComponent.builder().build()
        return DaggerAppComponent
                .builder()
                .create(this)
                .libraryComponent(repositoryComponent)
                .build()
    }

}
