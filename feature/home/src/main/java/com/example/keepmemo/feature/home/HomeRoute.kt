package com.example.keepmemo.feature.home

import android.app.Activity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.keepmemo.core.authentication.result.PublicKeyCreateResult
import com.example.keepmemo.core.authentication.result.PublicKeySignInResult
import com.example.keepmemo.core.authentication.util.PublicKeyCredentialManager
import com.example.keepmemo.core.authentication.util.PublicKeySignInManager
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
    navigateToAddKeep: () -> Unit,
    addKeepEvent: String,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    HomeRoute(
        uiState = uiState,
        openDrawer = openDrawer,
        onMessageDismiss = homeViewModel::dismissMessage,
        onHomeUiEvent = { event ->
            when (event) {
                HomeUiEvent.NavigateToAddKeep -> {
                    navigateToAddKeep()
                }
                is HomeUiEvent.NavigateToFullScreen -> {
                    homeViewModel.toFullScreen(event.memo)
                }
                HomeUiEvent.NavigateToMemoList -> {
                    coroutineScope.launch {
                        homeViewModel.saveKeep()
                        homeViewModel.toMemoList()
                    }
                }
                is HomeUiEvent.UpdateFullScreenMemoTitle -> {
                    homeViewModel.updateFullScreenMemoTitle(event.title)
                }
                is HomeUiEvent.UpdateFullScreenMemoBody -> {
                    homeViewModel.updateFullScreenMemoBody(event.body)
                }
                is HomeUiEvent.HomeListPageChange -> {
                    homeViewModel.updateListPane(event.homeListPane)
                }
                is HomeUiEvent.AddToSelectedIdList -> {
                    homeViewModel.addSelectedMemoId(event.keepId)
                }
                is HomeUiEvent.RemoveFromSelectedIdList -> {
                    homeViewModel.removeSelectedMemoId(event.keepId)
                }
                is HomeUiEvent.SignIn -> {
                    homeViewModel.startSignInFlow()
                }
            }
        },
        snackbarHostState = snackbarHostState
    )

    LaunchedEffect(uiState.loginFlowState) {
        when (uiState.loginFlowState) {
            LoginFlowState.SignUp -> {
                val result = PublicKeyCredentialManager.requestCreateCredential(
                    userName = "testUser", // TODO userNameの指定
                    activity = context as Activity
                )
                if (result is PublicKeyCreateResult.Success) {
                    homeViewModel.saveUser(
                        userId = result.userId,
                        userName = result.userName
                    )
                    homeViewModel.startSignInFlow()
                } else {
                    homeViewModel.finishSignInFlow()
                }
            }
            LoginFlowState.SignIn -> {
                val result = PublicKeySignInManager.requestSignIn(
                    activity = context as Activity
                )
                when (result) {
                    // 認証成功
                    is PublicKeySignInResult.Success -> {
                        homeViewModel.updateUser(
                            userId = result.userId,
                            isSigned = true
                        )
                        homeViewModel.finishSignInFlow()
                    }
                    // 登録済みのパスキーが存在しない
                    PublicKeySignInResult.NoCredential -> {
                        homeViewModel.startSignUpFlow()
                    }
                    else -> {
                        homeViewModel.finishSignInFlow()
                    }
                }
            }
            null -> {
            }
        }
    }

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
    onHomeUiEvent: (HomeUiEvent) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    HomeScreen(
        uiMessages = uiState.uiMessages,
        listPane = uiState.homeListPane,
        memoList = uiState.memoList,
        selectedMemoIdList = uiState.selectedMemoIdList,
        fullScreenMemo = uiState.fullScreenMemo,
        openDrawer = openDrawer,
        onMessageDismiss = onMessageDismiss,
        onHomeUiEvent = onHomeUiEvent,
        snackbarHostState = snackbarHostState
    )
}
