package com.toolslab.cowork.base_repository.converter

interface Converter<S, M> {
    fun convert(source: S): M
}
