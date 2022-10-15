package com.example.keepmemo.feature.keepdetail.di

import com.example.keepmemo.feature.keepdetail.AddOrEditMemoViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface MainActivityViewModelFactoryProvider {
    fun addOrEditKeepViewModelFactory(): AddOrEditMemoViewModel.Factory
}
