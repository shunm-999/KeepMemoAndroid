package com.example.keepmemo.ui.editkeep

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.keepmemo.di.MainActivityViewModelFactoryProvider
import dagger.hilt.android.EntryPointAccessors

@Composable
fun AddOrEditKeepRoute(
    targetKeepId: Long,
    editTime: Long,
    addOrEditViewModel: AddOrEditViewModel = addOrEditKeepViewModel(
        targetKeepId = targetKeepId,
        editTime = editTime
    ),
    onBackPressed: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val uiState by addOrEditViewModel.uiState.collectAsState()
    AddOrEditKeepRoute(
        uiState = uiState,
        onTitleChange = { title -> addOrEditViewModel.updateTitle(title) },
        onBodyChange = { body -> addOrEditViewModel.updateBody(body) },
        onBackPressed = {
            addOrEditViewModel.saveKeep()
            onBackPressed()
        },
        scaffoldState = scaffoldState
    )
    BackHandler(true) {
        addOrEditViewModel.saveKeep()
        onBackPressed()
    }
}

@Composable
fun AddOrEditKeepRoute(
    uiState: AddOrEditKeepUiState,
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
    targetKeepId: Long,
    editTime: Long
): AddOrEditViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivityViewModelFactoryProvider::class.java
    ).addOrEditKeepViewModelFactory()

    return viewModel(
        factory = AddOrEditViewModel.provideFactory(
            factory,
            targetKeepId = targetKeepId,
            editTime = editTime
        )
    )
}
