package com.toolslab.androidstarterkit.di

import android.app.Application
import com.toolslab.androidstarterkit.AndroidStarterKit
import com.toolslab.androidstarterkit.view.main.AndroidStarterKitModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            AppModule::class,
            ActivitiesBindingModule::class,
            AndroidStarterKitModule::class
        ]
)
interface AppComponent : AndroidInjector<AndroidStarterKit> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(app: Application): Builder

        fun build(): AppComponent
    }
}
