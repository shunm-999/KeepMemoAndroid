package com.example.keepmemo.util.ktx

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit

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
