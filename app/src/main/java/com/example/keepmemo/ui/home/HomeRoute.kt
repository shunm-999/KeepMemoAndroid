package com.example.keepmemo.ui.home

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    // UiState of the HomeScreen
    val uiState by homeViewModel.uiState.collectAsState()
    HomeRoute(
        uiState = uiState,
        openDrawer = openDrawer,
        listPaneChange = { listPane ->
            homeViewModel.updateListPane(listPane)
        },
        scaffoldState = scaffoldState
    )
}

@Composable
fun HomeRoute(
    uiState: HomeUiState,
    openDrawer: () -> Unit,
    listPaneChange: (HomeListPane) -> Unit,
    scaffoldState: ScaffoldState
) {
    val keepListLazyListState = rememberLazyListState()
    HomeScreen(
        listPane = uiState.homeListPane,
        keepList = uiState.keepMemoList,
        openDrawer = openDrawer,
        listPaneChange = listPaneChange,
        keepListLazyListState = keepListLazyListState,
        isShowTopAppBar = true,
        isShowBottomAppBar = true,
        scaffoldState = scaffoldState
    )
}
