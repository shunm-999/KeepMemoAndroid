package com.example.keepmemo.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Splitscreen
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.keepmemo.R
import com.example.keepmemo.model.Keep
import com.example.keepmemo.ui.ktx.isScrolled
import com.example.keepmemo.ui.theme.KeepMemoTheme

@Composable
fun HomeScreen(
    listPane: HomeListPane,
    keepList: List<Keep>,
    isShowTopAppBar: Boolean,
    isShowBottomAppBar: Boolean,
    openDrawer: () -> Unit,
    listPaneChange: (HomeListPane) -> Unit,
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
                // TODO
            }
        },
        modifier = modifier
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding)
        HomeScreenContent(
            listPane = listPane,
            keepList = keepList,
            keepListLazyListState = keepListLazyListState,
            modifier = contentModifier
        )
    }
}

@Composable
fun HomeScreenContent(
    listPane: HomeListPane,
    keepList: List<Keep>,
    keepListLazyListState: LazyListState,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier) {
        when (listPane) {
            HomeListPane.One -> {
                KeepListOneLine(
                    keepList = keepList,
                    keepListLazyListState = keepListLazyListState
                )
            }
            HomeListPane.Two -> {
                KeepListTwoGrid(
                    keepList = keepList,
                    keepListLazyListState = keepListLazyListState
                )
            }
        }
    }
}

@Composable
fun KeepListOneLine(
    keepList: List<Keep>,
    keepListLazyListState: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = keepListLazyListState
    ) {
        items(keepList) { keep ->
            KeepCard(
                title = keep.title,
                body = keep.body
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun KeepListTwoGrid(
    keepList: List<Keep>,
    keepListLazyListState: LazyListState,
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
        items(keepList) { keep ->
            KeepCard(
                title = keep.title,
                body = keep.body
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

@Preview("HomeScreen")
@Preview("HomeScreen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    KeepMemoTheme {
        HomeScreen(
            listPane = HomeListPane.One,
            keepList = listOf(
                Keep(
                    id = 1,
                    title = "title1",
                    body = "body1"
                ),
                Keep(
                    id = 2,
                    title = "title2",
                    body = "body2"
                ),
                Keep(
                    id = 3,
                    title = "title3",
                    body = "body3"
                ),
            ),
            isShowTopAppBar = true,
            isShowBottomAppBar = true,
            openDrawer = {},
            listPaneChange = {},
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
