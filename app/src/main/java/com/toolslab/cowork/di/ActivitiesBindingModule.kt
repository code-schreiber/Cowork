package com.toolslab.cowork.di

import com.toolslab.cowork.view.main.CoworkActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBindingModule {

    @ContributesAndroidInjector
    abstract fun androidStarterKitActivity(): CoworkActivity

}
