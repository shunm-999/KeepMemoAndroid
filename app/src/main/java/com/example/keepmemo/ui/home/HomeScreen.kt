package com.example.keepmemo.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Splitscreen
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.keepmemo.R
import com.example.keepmemo.model.Keep
import com.example.keepmemo.model.Memo
import com.example.keepmemo.ui.ktx.isScrolled
import com.example.keepmemo.ui.theme.KeepMemoTheme

@Composable
fun HomeScreen(
    listPane: HomeListPane,
    memoList: List<Memo>,
    selectedMemoIdList: Set<Long>,
    isShowTopAppBar: Boolean,
    isShowBottomAppBar: Boolean,
    openDrawer: () -> Unit,
    listPaneChange: (HomeListPane) -> Unit,
    navigateToAddKeep: () -> Unit,
    navigateToEditKeep: (Long) -> Unit,
    addToSelectedIdList: (Long) -> Unit,
    removeFromSelectedIdList: (Long) -> Unit,
    keepListLazyListState: LazyListState,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {},
        topBar = {
            if (isShowTopAppBar) {
                HomeTopAppBar(
                    listPane = listPane,
                    openDrawer = openDrawer,
                    listPaneChange = listPaneChange,
                    elevation = if (!keepListLazyListState.isScrolled) 0.dp else 4.dp
                )
            }
        },
        bottomBar = {
            if (isShowBottomAppBar) {
                HomeBottomBar(
                    modifier = Modifier.clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
                    cutoutShape = CircleShape,
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToAddKeep) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null
                )
            }
        },
        isFloatingActionButtonDocked = true,
        modifier = modifier
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding)
        HomeScreenContent(
            listPane = listPane,
            memoList = memoList,
            selectedMemoIdList = selectedMemoIdList,
            keepListLazyListState = keepListLazyListState,
            navigateToEditKeep = navigateToEditKeep,
            addToSelectedIdList = addToSelectedIdList,
            removeFromSelectedIdList = removeFromSelectedIdList,
            modifier = contentModifier
        )
    }
}

@Composable
fun HomeScreenContent(
    listPane: HomeListPane,
    memoList: List<Memo>,
    selectedMemoIdList: Set<Long>,
    keepListLazyListState: LazyListState,
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
                    keepListLazyListState = keepListLazyListState,
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MemoListTwoGrid(
    memoList: List<Memo>,
    selectedMemoIdList: Set<Long>,
    keepListLazyListState: LazyListState,
    navigateToEditKeep: (Long) -> Unit,
    addToSelectedIdList: (Long) -> Unit,
    removeFromSelectedIdList: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        state = keepListLazyListState,
        cells = GridCells.Fixed(2),
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
@Composable
private fun HomeTopAppBar(
    elevation: Dp,
    listPane: HomeListPane,
    openDrawer: () -> Unit,
    listPaneChange: (HomeListPane) -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.home_memo_appbar_title))
        },
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
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
                        HomeListPane.One -> Icons.Filled.Splitscreen
                        HomeListPane.Two -> Icons.Filled.GridView
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
        backgroundColor = MaterialTheme.colors.surface,
        elevation = elevation
    )
}

@Composable
private fun HomeBottomBar(
    modifier: Modifier = Modifier,
    cutoutShape: Shape? = null,
) {
    BottomAppBar(
        modifier = modifier,
        cutoutShape = cutoutShape
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
        ) {
        }
    }
}

@Preview("HomeScreen")
@Preview("HomeScreen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    KeepMemoTheme {
        HomeScreen(
            listPane = HomeListPane.One,
            memoList = listOf(
                Memo.EMPTY.copy(
                    keep = Keep(
                        id = 1L,
                        title = "title1",
                        body = "body1",
                    )
                ),
                Memo.EMPTY.copy(
                    keep = Keep(
                        id = 2L,
                        title = "title2",
                        body = "body2",
                    )
                ),
                Memo.EMPTY.copy(
                    keep = Keep(
                        id = 3L,
                        title = "title3",
                        body = "body3",
                    )
                ),
            ),
            selectedMemoIdList = setOf(1L),
            isShowTopAppBar = true,
            isShowBottomAppBar = true,
            openDrawer = {},
            listPaneChange = {},
            navigateToAddKeep = {},
            navigateToEditKeep = {},
            addToSelectedIdList = {},
            removeFromSelectedIdList = {},
            keepListLazyListState = rememberLazyListState(),
            scaffoldState = rememberScaffoldState()
        )
    }
}

@Preview("HomeTopAppBar")
@Preview("HomeTopAppBar (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeTopAppBarPreview() {
    KeepMemoTheme {
        HomeTopAppBar(
            elevation = 0.dp,
            listPane = HomeListPane.One,
            openDrawer = {},
            listPaneChange = {}
        )
    }
}
