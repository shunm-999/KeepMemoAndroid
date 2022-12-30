package com.example.keepmemo.feature.keepdetail.di

import com.example.keepmemo.feature.keepdetail.AddOrEditKeepViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface MainActivityViewModelFactoryProvider {
    fun addOrEditKeepViewModelFactory(): AddOrEditKeepViewModel.Factory
}
