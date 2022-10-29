package com.example.keepmemo.feature.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.GridView
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.keepmemo.core.designsystem.component.KeepMemoSnackbarHost
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
    isShowTopAppBar: Boolean,
    isShowBottomAppBar: Boolean,
    openDrawer: () -> Unit,
    onMessageDismiss: (Long) -> Unit,
    listPaneChange: (HomeListPane) -> Unit,
    navigateToAddKeep: () -> Unit,
    navigateToEditKeep: (Long) -> Unit,
    addToSelectedIdList: (Long) -> Unit,
    removeFromSelectedIdList: (Long) -> Unit,
    keepListLazyListState: LazyListState,
    keepListLazyGridState: LazyGridState,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        snackbarHost = { KeepMemoSnackbarHost(hostState = snackbarHostState) },
        topBar = {
            if (isShowTopAppBar) {
                HomeTopAppBar(
                    listPane = listPane,
                    openDrawer = openDrawer,
                    listPaneChange = listPaneChange,
                    scrollBehavior = scrollBehavior
                )
            }
        },
        bottomBar = {
            if (isShowBottomAppBar) {
                HomeBottomBar(
                    onClickFloatingActionButton = navigateToAddKeep,
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
            keepListLazyListState = keepListLazyListState,
            keepListLazyGridState = keepListLazyGridState,
            navigateToEditKeep = navigateToEditKeep,
            addToSelectedIdList = addToSelectedIdList,
            removeFromSelectedIdList = removeFromSelectedIdList,
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
    keepListLazyListState: LazyListState,
    keepListLazyGridState: LazyGridState,
    navigateToEditKeep: (Long) -> Unit,
    addToSelectedIdList: (Long) -> Unit,
    removeFromSelectedIdList: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier) {
        when (listPane) {
            HomeListPane.One -> {
                MemoListOneLine(
                    memoList = memoList,
                    selectedMemoIdList = selectedMemoIdList,
                    keepListLazyListState = keepListLazyListState,
                    navigateToEditKeep = navigateToEditKeep,
                    addToSelectedIdList = addToSelectedIdList,
                    removeFromSelectedIdList = removeFromSelectedIdList
                )
            }
            HomeListPane.Two -> {
                MemoListTwoGrid(
                    memoList = memoList,
                    selectedMemoIdList = selectedMemoIdList,
                    keepListLazyGridState = keepListLazyGridState,
                    navigateToEditKeep = navigateToEditKeep,
                    addToSelectedIdList = addToSelectedIdList,
                    removeFromSelectedIdList = removeFromSelectedIdList
                )
            }
        }
    }
}

@Composable
fun MemoListOneLine(
    memoList: List<Memo>,
    selectedMemoIdList: Set<Long>,
    keepListLazyListState: LazyListState,
    navigateToEditKeep: (Long) -> Unit,
    addToSelectedIdList: (Long) -> Unit,
    removeFromSelectedIdList: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = keepListLazyListState
    ) {
        items(memoList) { memo ->
            val isSelected = selectedMemoIdList.contains(memo.id)
            KeepCard(
                title = memo.keep.title,
                body = memo.keep.body,
                onClick = {
                    navigateToEditKeep(memo.id)
                },
                onLongClick = {
                    if (isSelected) {
                        removeFromSelectedIdList(memo.id)
                    } else {
                        addToSelectedIdList(memo.id)
                    }
                },
                isSelected = isSelected
            )
        }
    }
}

@Composable
fun MemoListTwoGrid(
    memoList: List<Memo>,
    selectedMemoIdList: Set<Long>,
    keepListLazyGridState: LazyGridState,
    navigateToEditKeep: (Long) -> Unit,
    addToSelectedIdList: (Long) -> Unit,
    removeFromSelectedIdList: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        state = keepListLazyGridState,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(memoList) { memo ->
            val isSelected = selectedMemoIdList.contains(memo.id)
            KeepCard(
                title = memo.keep.title,
                body = memo.keep.body,
                onClick = {
                    navigateToEditKeep(memo.id)
                },
                onLongClick = {
                    if (isSelected) {
                        removeFromSelectedIdList(memo.id)
                    } else {
                        addToSelectedIdList(memo.id)
                    }
                },
                isSelected = isSelected
            )
        }
    }
}

/**
 * TopAppBar for the Home screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    listPane: HomeListPane,
    openDrawer: () -> Unit,
    listPaneChange: (HomeListPane) -> Unit
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.home_memo_appbar_title))
        },
        navigationIcon = {
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
        },
        actions = {
            IconButton(onClick = {
                listPaneChange(
                    when (listPane) {
                        HomeListPane.One -> HomeListPane.Two
                        HomeListPane.Two -> HomeListPane.One
                    }
                )
            }) {
                Icon(
                    imageVector = when (listPane) {
                        HomeListPane.One -> Icons.Filled.GridView
                        HomeListPane.Two -> Icons.Filled.Splitscreen
                    },
                    contentDescription = null
                )
            }
            IconButton(onClick = { /* TODO: Open search */ }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun HomeBottomBar(
    onClickFloatingActionButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        floatingActionButton = {
            FloatingActionButton(onClick = onClickFloatingActionButton) {
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
            ),
            selectedMemoIdList = setOf(1L),
            isShowTopAppBar = true,
            isShowBottomAppBar = true,
            openDrawer = {},
            onMessageDismiss = {},
            listPaneChange = {},
            navigateToAddKeep = {},
            navigateToEditKeep = {},
            addToSelectedIdList = {},
            removeFromSelectedIdList = {},
            keepListLazyListState = rememberLazyListState(),
            keepListLazyGridState = rememberLazyGridState(),
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
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
            listPane = HomeListPane.One,
            openDrawer = {},
            listPaneChange = {}
        )
    }
}
