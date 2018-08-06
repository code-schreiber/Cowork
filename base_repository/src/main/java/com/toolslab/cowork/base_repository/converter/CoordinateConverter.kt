package com.toolslab.cowork.base_repository.converter

import android.support.annotation.VisibleForTesting
import javax.inject.Inject

class CoordinateConverter @Inject constructor() : Converter<String, Double> {

    @VisibleForTesting
    internal val default = 0.0

    override fun convert(source: String): Double {
        try {
            return source.toDouble()
        } catch (e: Exception) {
            // TODO log exception
            return default
        }
    }

}
