package com.toolslab.cowork.util

internal fun List<Double>.median(): Double {
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

