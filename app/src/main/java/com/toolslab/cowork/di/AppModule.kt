package com.toolslab.cowork.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class AppModule {

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()


    @Provides
    fun provideApplicationContext(application: Application): Context = application.applicationContext

}
