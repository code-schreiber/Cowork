package com.toolslab.cowork.app.util

import kotlin.collections.min
import kotlin.collections.max

internal fun List<Double>.median(): Double {
    if (this.isEmpty()) return 0.0

    val sorted = this.sorted()
    val middleIndex = sorted.size / 2
    val middle = sorted[middleIndex]
    return if (sorted.size % 2 == 1) {
        middle
    } else {
        val oneBeforeMiddle = sorted[middleIndex - 1]
        (oneBeforeMiddle + middle) / 2.0
    }
}

internal fun List<Double>.min(): Double {
    if (this.isEmpty()) return 0.0
    return this.min()!!
}

internal fun List<Double>.max(): Double {
    if (this.isEmpty()) return 0.0
    return this.max()!!
}

