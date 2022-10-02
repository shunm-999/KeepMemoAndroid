package com.example.keepmemo.core.common.util.ktx

import java.util.concurrent.TimeUnit
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun intervalFlow(period: Long, unit: TimeUnit, takeWhile: (Long) -> Boolean): Flow<Long> {
    return flow<Long> {
        var counter = 0L
        while (takeWhile(counter)) {
            counter++
            emit(counter)
            delay(period * unit.toMillis(1L))
        }
    }
}
