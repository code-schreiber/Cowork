package com.toolslab.cowork.di

import android.app.Application
import com.toolslab.base_repository.di.LibraryComponent
import com.toolslab.cowork.Cowork
import com.toolslab.cowork.view.main.CoworkModule
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
            CoworkModule::class
        ],
        dependencies = [
            LibraryComponent::class
        ]
)
interface AppComponent : AndroidInjector<Cowork> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(app: Application): Builder

        fun libraryComponent(libraryComponent: LibraryComponent): Builder

        fun build(): AppComponent
    }
}
