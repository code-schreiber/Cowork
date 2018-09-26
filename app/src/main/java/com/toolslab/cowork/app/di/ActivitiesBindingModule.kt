package com.toolslab.cowork.app.di

import com.toolslab.cowork.app.view.map.CoworkActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBindingModule {

    @ContributesAndroidInjector
    abstract fun coworkActivity(): CoworkActivity

}
