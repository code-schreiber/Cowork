package com.toolslab.cowork.base_repository.converter

interface ModelConverter<S, M> {
    fun convert(source: S): M
}
