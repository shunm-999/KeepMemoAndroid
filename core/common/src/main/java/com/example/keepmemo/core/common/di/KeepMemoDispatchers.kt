package com.example.keepmemo.core.common.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val keepMemoDispatcher: KeepMemoDispatchers)

enum class KeepMemoDispatchers {
    DEFAULT,
    IO
}
