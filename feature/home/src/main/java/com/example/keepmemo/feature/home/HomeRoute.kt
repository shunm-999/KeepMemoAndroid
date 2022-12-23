package com.example.keepmemo.feature.home

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
    navigateToAddKeep: () -> Unit,
    navigateToEditKeep: (Long) -> Unit,
    addKeepEvent: String,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    // UiState of the HomeScreen
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    HomeRoute(
        uiState = uiState,
        openDrawer = openDrawer,
        onMessageDismiss = { id ->
            homeViewModel.onShowMessage(id)
        },
        navigateToAddKeep = navigateToAddKeep,
        navigateToEditKeep = navigateToEditKeep,
        listPaneChange = { listPane ->
            homeViewModel.updateListPane(listPane)
        },
        addToSelectedIdList = { memoId ->
            homeViewModel.addSelectedMemoId(memoId)
        },
        removeFromSelectedIdList = { memoId ->
            homeViewModel.removeSelectedMemoId(memoId)
        },
        snackbarHostState = snackbarHostState
    )

    LaunchedEffect(addKeepEvent) {
        snapshotFlow { addKeepEvent }
            .distinctUntilChanged()
            .filter { it.isNotEmpty() }
            .collect {
                homeViewModel.showMessage(R.string.snackbar_message_add_keep)
            }
    }
}

@Composable
fun HomeRoute(
    uiState: HomeUiState,
    openDrawer: () -> Unit,
    onMessageDismiss: (Long) -> Unit,
    navigateToAddKeep: () -> Unit,
    navigateToEditKeep: (Long) -> Unit,
    listPaneChange: (HomeListPane) -> Unit,
    addToSelectedIdList: (Long) -> Unit,
    removeFromSelectedIdList: (Long) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val keepListLazyGridState = rememberLazyGridState()
    HomeScreen(
        uiMessages = uiState.uiMessages,
        listPane = uiState.homeListPane,
        memoList = uiState.memoList,
        selectedMemoIdList = uiState.selectedMemoIdList,
        openDrawer = openDrawer,
        onMessageDismiss = onMessageDismiss,
        navigateToAddKeep = navigateToAddKeep,
        navigateToEditKeep = navigateToEditKeep,
        listPaneChange = listPaneChange,
        addToSelectedIdList = addToSelectedIdList,
        removeFromSelectedIdList = removeFromSelectedIdList,
        keepListLazyGridState = keepListLazyGridState,
        snackbarHostState = snackbarHostState
    )
}
