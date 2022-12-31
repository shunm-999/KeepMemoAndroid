package com.example.keepmemo.feature.keepdetail

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.keepmemo.core.common.result.Result
import com.example.keepmemo.feature.keepdetail.di.MainActivityViewModelFactoryProvider
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch

sealed interface AddOrEditKeepEvent {
    object ADDED : AddOrEditKeepEvent
    object EDITED : AddOrEditKeepEvent
    object NONE : AddOrEditKeepEvent
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun AddOrEditKeepRoute(
    targetId: Long,
    editTime: Long,
    addOrEditKeepViewModel: AddOrEditKeepViewModel = addOrEditKeepViewModel(
        targetId = targetId,
        editTime = editTime
    ),
    onBackPressed: (AddOrEditKeepEvent) -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState by addOrEditKeepViewModel.uiState.collectAsStateWithLifecycle()
    (uiState as? AddOrEditKeepUiState.INITIALIZED)?.let {
        AddOrEditKeepRoute(
            uiState = it,
            onTitleChange = { title -> addOrEditKeepViewModel.updateTitle(title) },
            onBodyChange = { body -> addOrEditKeepViewModel.updateBody(body) },
            onBackPressed = {
                coroutineScope.launch {
                    val event = when (addOrEditKeepViewModel.saveKeep()) {
                        is Result.Success -> {
                            if (targetId > 0) {
                                AddOrEditKeepEvent.EDITED
                            } else {
                                AddOrEditKeepEvent.ADDED
                            }
                        }
                        is Result.Error -> {
                            AddOrEditKeepEvent.NONE
                        }
                    }
                    onBackPressed(event)
                }
            },
            snackbarHostState = snackbarHostState
        )
        BackHandler(true) {
            coroutineScope.launch {
                val event = when (addOrEditKeepViewModel.saveKeep()) {
                    is Result.Success -> {
                        if (targetId > 0) {
                            AddOrEditKeepEvent.EDITED
                        } else {
                            AddOrEditKeepEvent.ADDED
                        }
                    }
                    is Result.Error -> {
                        AddOrEditKeepEvent.NONE
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
    snackbarHostState: SnackbarHostState
) {
    AddOrEditKeepScreen(
        title = uiState.title,
        body = uiState.body,
        onTitleChange = onTitleChange,
        onBodyChange = onBodyChange,
        onBackPressed = onBackPressed,
        snackbarHostState = snackbarHostState
    )
}

@Composable
private fun addOrEditKeepViewModel(
    targetId: Long,
    editTime: Long
): AddOrEditKeepViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivityViewModelFactoryProvider::class.java
    ).addOrEditKeepViewModelFactory()

    return viewModel(
        factory = AddOrEditKeepViewModel.provideFactory(
            factory,
            targetId = targetId,
            editTime = editTime
        )
    )
}
