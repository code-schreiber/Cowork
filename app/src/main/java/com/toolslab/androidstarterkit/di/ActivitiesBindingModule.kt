package com.toolslab.androidstarterkit.di

import com.toolslab.androidstarterkit.view.main.AndroidStarterKitActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBindingModule {

    @ContributesAndroidInjector
    abstract fun androidStarterKitActivity(): AndroidStarterKitActivity

}
