package com.example.keepmemo.feature.keepdetail

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.keepmemo.core.common.result.Result
import com.example.keepmemo.feature.keepdetail.di.MainActivityViewModelFactoryProvider
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch

sealed interface AddOrEditKeepRouteEvent {
    object ADDED : AddOrEditKeepRouteEvent
    object EDITED : AddOrEditKeepRouteEvent
    object NONE : AddOrEditKeepRouteEvent
}

@Composable
fun AddOrEditKeepRoute(
    targetId: Long,
    editTime: Long,
    addOrEditMemoViewModel: AddOrEditMemoViewModel = addOrEditKeepViewModel(
        targetId = targetId,
        editTime = editTime
    ),
    onBackPressed: (AddOrEditKeepRouteEvent) -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState = addOrEditMemoViewModel.uiState.collectAsState().value
    if (uiState is AddOrEditKeepUiState.INITIALIZED) {
        AddOrEditKeepRoute(
            uiState = uiState,
            onTitleChange = { title -> addOrEditMemoViewModel.updateTitle(title) },
            onBodyChange = { body -> addOrEditMemoViewModel.updateBody(body) },
            onBackPressed = {
                coroutineScope.launch {
                    val event = when (addOrEditMemoViewModel.saveKeep()) {
                        is Result.Success -> {
                            if (targetId > 0) {
                                AddOrEditKeepRouteEvent.EDITED
                            } else {
                                AddOrEditKeepRouteEvent.ADDED
                            }
                        }
                        is Result.Error -> {
                            AddOrEditKeepRouteEvent.NONE
                        }
                    }
                    onBackPressed(event)
                }
            },
            scaffoldState = scaffoldState
        )
        BackHandler(true) {
            coroutineScope.launch {
                val event = when (addOrEditMemoViewModel.saveKeep()) {
                    is Result.Success -> {
                        if (targetId > 0) {
                            AddOrEditKeepRouteEvent.EDITED
                        } else {
                            AddOrEditKeepRouteEvent.ADDED
                        }
                    }
                    is Result.Error -> {
                        AddOrEditKeepRouteEvent.NONE
                    }
                }
                onBackPressed(event)
            }
        }
    }
}

@Composable
fun AddOrEditKeepRoute(
    uiState: AddOrEditKeepUiState.INITIALIZED,
    onTitleChange: (String) -> Unit,
    onBodyChange: (String) -> Unit,
    onBackPressed: () -> Unit,
    scaffoldState: ScaffoldState
) {
    AddOrEditKeepScreen(
        title = uiState.title,
        body = uiState.body,
        onTitleChange = onTitleChange,
        onBodyChange = onBodyChange,
        onBackPressed = onBackPressed,
        scaffoldState = scaffoldState
    )
}

@Composable
private fun addOrEditKeepViewModel(
    targetId: Long,
    editTime: Long
): AddOrEditMemoViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivityViewModelFactoryProvider::class.java
    ).addOrEditKeepViewModelFactory()

    return viewModel(
        factory = AddOrEditMemoViewModel.provideFactory(
            factory,
            targetId = targetId,
            editTime = editTime
        )
    )
}
