package com.example.keepmemo.feature.home

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

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
    val uiState by homeViewModel.uiState.collectAsState()
    HomeRoute(
        uiState = uiState,
        openDrawer = openDrawer,
        listPaneChange = { listPane ->
            homeViewModel.updateListPane(listPane)
        },
        onMessageDismiss = { id ->
            homeViewModel.onShowMessage(id)
        },
        navigateToAddKeep = navigateToAddKeep,
        navigateToEditKeep = navigateToEditKeep,
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
    listPaneChange: (HomeListPane) -> Unit,
    navigateToAddKeep: () -> Unit,
    navigateToEditKeep: (Long) -> Unit,
    addToSelectedIdList: (Long) -> Unit,
    removeFromSelectedIdList: (Long) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val keepListLazyListState = rememberLazyListState()
    val keepListLazyGridState = rememberLazyGridState()
    HomeScreen(
        uiMessages = uiState.uiMessages,
        listPane = uiState.homeListPane,
        memoList = uiState.memoList,
        selectedMemoIdList = uiState.selectedMemoIdList,
        openDrawer = openDrawer,
        onMessageDismiss = onMessageDismiss,
        listPaneChange = listPaneChange,
        navigateToAddKeep = navigateToAddKeep,
        navigateToEditKeep = navigateToEditKeep,
        addToSelectedIdList = addToSelectedIdList,
        removeFromSelectedIdList = removeFromSelectedIdList,
        keepListLazyListState = keepListLazyListState,
        keepListLazyGridState = keepListLazyGridState,
        isShowTopAppBar = true,
        isShowBottomAppBar = true,
        snackbarHostState = snackbarHostState
    )
}
