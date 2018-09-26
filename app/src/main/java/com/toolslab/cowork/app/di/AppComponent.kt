package com.toolslab.cowork.app.di

import android.app.Application
import com.toolslab.cowork.app.Cowork
import com.toolslab.cowork.app.view.map.CoworkModule
import com.toolslab.cowork.base_repository.di.RepositoryComponent
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
            RepositoryComponent::class
        ]
)
interface AppComponent : AndroidInjector<Cowork> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(app: Application): Builder

        fun repositoryComponent(repositoryComponent: RepositoryComponent): Builder

        fun build(): AppComponent
    }
}
