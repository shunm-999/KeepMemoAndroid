package com.example.keepmemo.core.common.util

import java.util.concurrent.TimeUnit
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

fun <T1, T2, T3, T4, T5, T6, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    transform: suspend (T1, T2, T3, T4, T5, T6) -> R
): Flow<R> = combine(
    combine(flow, flow2, flow3, ::Triple),
    combine(flow4, flow5, flow6, ::Triple)
) { t1, t2 ->
    transform(
        t1.first,
        t1.second,
        t1.third,
        t2.first,
        t2.second,
        t2.third
    )
}

fun <T1, T2, T3, T4, T5, T6, T7, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    transform: suspend (T1, T2, T3, T4, T5, T6, T7) -> R
): Flow<R> = combine(
    combine(flow, flow2, flow3, flow4, flow5, flow6) { T1, T2, T3, T4, T5, T6 ->
        Pair(Triple(T1, T2, T3), Triple(T4, T5, T6))
    },
    flow7
) { t1, t2 ->

    transform(
        t1.first.first,
        t1.first.second,
        t1.first.third,
        t1.second.first,
        t1.second.second,
        t1.second.third,
        t2
    )
}

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
