package com.example.keepmemo.di

import com.example.keepmemo.ui.editkeep.AddOrEditMemoViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface MainActivityViewModelFactoryProvider {
    fun addOrEditKeepViewModelFactory(): AddOrEditMemoViewModel.Factory
}
