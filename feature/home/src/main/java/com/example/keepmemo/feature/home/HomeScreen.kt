package com.example.keepmemo.feature.home

import android.content.Context
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Splitscreen
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.LookaheadLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.keepmemo.core.designsystem.component.KeepMemoSnackbarHost
import com.example.keepmemo.core.designsystem.ktx.ImmutableList.Companion.toImmutableList
import com.example.keepmemo.core.designsystem.ktx.animateConstraints
import com.example.keepmemo.core.designsystem.theme.KeepMemoTheme
import com.example.keepmemo.core.designsystem.theme.LocalIsShowingDrawer
import com.example.keepmemo.core.model.data.Keep
import com.example.keepmemo.core.model.data.Memo
import com.example.keepmemo.core.model.data.UiMessage
import com.example.keepmemo.core.ui.KeepCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiMessages: List<UiMessage>,
    listPane: HomeListPane,
    memoList: List<Memo>,
    selectedMemoIdList: Set<Long>,
    fullScreenMemo: Memo?,
    openDrawer: () -> Unit,
    onMessageDismiss: (Long) -> Unit,
    onHomeUiEvent: (HomeUiEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val isFullScreen = fullScreenMemo != null

    Scaffold(
        snackbarHost = { KeepMemoSnackbarHost(hostState = snackbarHostState) },
        topBar = {
            HomeTopAppBar(
                isFullScreen = isFullScreen,
                scrollBehavior = scrollBehavior,
                listPane = listPane,
                openDrawer = openDrawer,
                onBackPressed = {
                    onHomeUiEvent(
                        HomeUiEvent.NavigateToMemoList
                    )
                },
                onListPaneChange = { listPane ->
                    onHomeUiEvent(
                        HomeUiEvent.HomeListPageChange(listPane)
                    )
                },
                onSignIn = {
                    onHomeUiEvent(
                        HomeUiEvent.SignIn
                    )
                }
            )
        },
        bottomBar = {
            if (!isFullScreen) {
                HomeBottomBar(
                    onClickFloatingActionButton = {
                        onHomeUiEvent(
                            HomeUiEvent.NavigateToAddKeep
                        )
                    },
                    modifier = Modifier.clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
                )
            }
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding)
        HomeScreenContent(
            listPane = listPane,
            memoList = memoList,
            selectedMemoIdList = selectedMemoIdList,
            fullScreenMemo = fullScreenMemo,
            onHomeUiEvent = onHomeUiEvent,
            modifier = contentModifier
        )
    }

    if (uiMessages.isNotEmpty()) {
        val uiMessage = remember(uiMessages) {
            uiMessages[0]
        }

        val uiMessageText: String = stringResource(uiMessage.messageId)
        val onMessageDismissState by rememberUpdatedState(onMessageDismiss)

        LaunchedEffect(uiMessageText, snackbarHostState) {
            snackbarHostState.showSnackbar(
                message = uiMessageText
            )
            onMessageDismissState(uiMessage.id)
        }
    }
}

@Composable
fun HomeScreenContent(
    listPane: HomeListPane,
    memoList: List<Memo>,
    selectedMemoIdList: Set<Long>,
    fullScreenMemo: Memo?,
    onHomeUiEvent: (HomeUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier) {
        // メモのリストと、メモの詳細（フルスクリーン）の表示
        MemoListAndFullScreen(
            columns = when (listPane) {
                HomeListPane.One -> 1
                HomeListPane.Two -> 2
            },
            memoList = memoList,
            selectedMemoIdList = selectedMemoIdList,
            fullScreenMemo = fullScreenMemo,
            onHomeUiEvent = onHomeUiEvent
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun MemoListAndFullScreen(
    columns: Int,
    memoList: List<Memo>,
    selectedMemoIdList: Set<Long>,
    fullScreenMemo: Memo?,
    onHomeUiEvent: (HomeUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val keepListLazyGridState = rememberLazyGridState()

    val isFullScreen = fullScreenMemo != null

    val keepCardComponents = remember(memoList) {
        memoList.associate {
            it.id to movableKeepCard(context)
        }
    }

    BackHandler(isFullScreen) {
        onHomeUiEvent(
            HomeUiEvent.NavigateToMemoList
        )
    }

    Box(
        modifier = modifier
    ) {
        // メモのリスト
        LazyVerticalGrid(
            modifier = Modifier.fillMaxHeight(),
            state = keepListLazyGridState,
            columns = GridCells.Fixed(columns),
            contentPadding = PaddingValues(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = memoList,
                key = { memo -> memo.id }
            ) { memo ->
                val isSelected = selectedMemoIdList.contains(memo.id)
                Column(
                    modifier = Modifier
                        .animateItemPlacement()
                ) {
                    keepCardComponents[memo.id]?.invoke(
                        MovableContentKeepCard(
                            title = memo.keep.title,
                            body = memo.keep.body,
                            isSelected = isSelected,
                            isFullScreen = false,
                            onClick = {
                                onHomeUiEvent(
                                    HomeUiEvent.NavigateToFullScreen(memo)
                                )
                            },
                            onLongClick = {
                                if (isSelected) {
                                    onHomeUiEvent(
                                        HomeUiEvent.RemoveFromSelectedIdList(memo.id)
                                    )
                                } else {
                                    onHomeUiEvent(
                                        HomeUiEvent.AddToSelectedIdList(memo.id)
                                    )
                                }
                            }
                        )
                    )
                }
            }
        }

        // メモの詳細
        LookaheadLayout(
            content = {
                Column(
                    modifier = if (isFullScreen) {
                        Modifier.fillMaxSize()
                    } else {
                        Modifier.size(0.dp)
                    }.animateConstraints(this@LookaheadLayout)
                ) {
                    val savedFullScreenMemo: Memo? by produceState<Memo?>(
                        initialValue = null,
                        fullScreenMemo
                    ) {
                        if (fullScreenMemo != null) {
                            value = fullScreenMemo
                        }
                    }
                    savedFullScreenMemo?.let { memo ->

                        keepCardComponents[memo.id]?.invoke(
                            MovableContentKeepCard(
                                title = memo.keep.title,
                                body = memo.keep.body,
                                isSelected = false,
                                isFullScreen = true,
                                onClick = {
                                },
                                onLongClick = {
                                },
                                onTitleChange = { title ->
                                    onHomeUiEvent(
                                        HomeUiEvent.UpdateFullScreenMemoTitle(
                                            title = title
                                        )
                                    )
                                },
                                onBodyChange = { body ->
                                    onHomeUiEvent(
                                        HomeUiEvent.UpdateFullScreenMemoBody(
                                            body = body
                                        )
                                    )
                                }
                            )
                        )
                    }
                }
            }
        ) { measurables, constraints ->
            val placeables = measurables.map { it.measure(constraints) }
            val maxWidth = placeables.maxOf { it.width }
            val maxHeight = placeables.maxOf { it.height }

            layout(maxWidth, maxHeight) {
                placeables.forEach {
                    it.place(0, 0)
                }
            }
        }
    }
}

private data class MovableContentKeepCard(
    val title: String,
    val body: String,
    val isSelected: Boolean,
    val isFullScreen: Boolean,
    val onClick: () -> Unit,
    val onLongClick: () -> Unit,
    val onTitleChange: (String) -> Unit = {},
    val onBodyChange: (String) -> Unit = {}
)

private fun movableKeepCard(
    context: Context
) = movableContentOf<MovableContentKeepCard>
{ (title, body, isSelected, isFullScreen, onClick, onLongClick, onTitleChange, onBodyChange) ->
    KeepCard(
        title = title,
        body = body,
        onClick = onClick,
        onLongClick = onLongClick,
        onTitleChange = onTitleChange,
        onBodyChange = onBodyChange,
        isSelected = isSelected,
        isFullScreen = isFullScreen,
        modifier = Modifier.semantics {
            contentDescription =
                context.getString(R.string.modifier_semantics_home_list_keep_card)
        }
    )
}

/**
 * TopAppBar for the Home screen
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
private fun HomeTopAppBar(
    isFullScreen: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    listPane: HomeListPane,
    openDrawer: () -> Unit,
    onBackPressed: () -> Unit,
    onListPaneChange: (HomeListPane) -> Unit,
    onSignIn: () -> Unit
) {
    val context = LocalContext.current

    TopAppBar(
        title = {
            if (!isFullScreen) {
                Text(text = stringResource(id = R.string.home_memo_appbar_title))
            }
        },
        navigationIcon = {
            if (isFullScreen) {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            } else {
                val isShowingDrawer = LocalIsShowingDrawer.current
                if (isShowingDrawer) {
                    IconButton(onClick = openDrawer) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        },
        actions = if (isFullScreen) {
            {}
        } else {
            {
                IconButton(
                    onClick = {
                        onListPaneChange(
                            when (listPane) {
                                HomeListPane.One -> HomeListPane.Two
                                HomeListPane.Two -> HomeListPane.One
                            }
                        )
                    },
                    modifier = Modifier.semantics {
                        contentDescription =
                            context.getString(R.string.modifier_semantics_home_list_pane_button)
                    }
                ) {
                    Icon(
                        imageVector = when (listPane) {
                            HomeListPane.One -> Icons.Filled.GridView
                            HomeListPane.Two -> Icons.Filled.Splitscreen
                        },
                        contentDescription = when (listPane) {
                            HomeListPane.One -> Icons.Filled.GridView.name
                            HomeListPane.Two -> Icons.Filled.Splitscreen.name
                        }
                    )
                }
                IconButton(onClick = { /* TODO: Open search */ }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null
                    )
                }
                IconButton(onClick = onSignIn) {
                    Icon(
                        imageVector = Icons.Filled.Login,
                        contentDescription = null
                    )
                }
            }
        },
        scrollBehavior = if (isFullScreen) {
            null
        } else {
            scrollBehavior
        }
    )
}

@Composable
private fun HomeBottomBar(
    onClickFloatingActionButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    BottomAppBar(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClickFloatingActionButton,
                modifier = Modifier.semantics {
                    contentDescription =
                        context.getString(R.string.modifier_semantics_add_keep_button)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null
                )
            }
        },
        actions = {},
        modifier = modifier
    )
}

@Preview("HomeScreen")
@Preview("HomeScreen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    KeepMemoTheme {
        HomeScreen(
            uiMessages = emptyList<UiMessage>(),
            listPane = HomeListPane.One,
            memoList = listOf(
                Memo.EMPTY.copy(
                    keep = Keep(
                        id = 1L,
                        title = "title1",
                        body = "body1"
                    )
                ),
                Memo.EMPTY.copy(
                    keep = Keep(
                        id = 2L,
                        title = "title2",
                        body = "body2"
                    )
                ),
                Memo.EMPTY.copy(
                    keep = Keep(
                        id = 3L,
                        title = "title3",
                        body = "body3"
                    )
                )
            ).toImmutableList(),
            selectedMemoIdList = setOf(1L),
            fullScreenMemo = null,
            openDrawer = {},
            onMessageDismiss = {},
            onHomeUiEvent = {},
            snackbarHostState = remember { SnackbarHostState() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("HomeTopAppBar")
@Preview("HomeTopAppBar (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeTopAppBarPreview() {
    KeepMemoTheme {
        HomeTopAppBar(
            isFullScreen = false,
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
            listPane = HomeListPane.One,
            openDrawer = {},
            onBackPressed = {},
            onListPaneChange = {},
            onSignIn = {}
        )
    }
}
