package com.toolslab.cowork.base_repository.di

import com.toolslab.cowork.base_network.di.NetworkModule
import com.toolslab.cowork.base_network.service.CoworkingMapService
import dagger.Component

@Component(
        modules = [NetworkModule::class]
)
interface RepositoryComponent {

    // Provision method so CoworkingMapService gets provided to consumers of this component
    fun provideCoworkingMapService(): CoworkingMapService

    @Component.Builder
    interface Builder {
        fun build(): RepositoryComponent
    }

}
