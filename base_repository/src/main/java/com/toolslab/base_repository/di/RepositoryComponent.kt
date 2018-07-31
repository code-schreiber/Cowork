package com.toolslab.base_repository.di

import com.toolslab.base_network.CoworkingMapService
import com.toolslab.base_network.di.NetworkModule
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
