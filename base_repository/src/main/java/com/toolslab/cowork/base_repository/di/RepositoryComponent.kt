package com.toolslab.cowork.base_repository.di

import dagger.Component

@Component
interface RepositoryComponent {

    @Component.Builder
    interface Builder {
        fun build(): RepositoryComponent
    }

}
