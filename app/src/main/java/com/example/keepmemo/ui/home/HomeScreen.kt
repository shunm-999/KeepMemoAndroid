package com.example.keepmemo.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.keepmemo.model.Keep

@Composable
fun HomeScreen(
    keepList: List<Keep>,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {},
        topBar = {},
        modifier = modifier
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding)
        HomeScreenContent(keepList = keepList, modifier = contentModifier)
    }
}

@Composable
fun HomeScreenContent(
    keepList: List<Keep>,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier) {
        val keepListLazyListState = rememberLazyListState()
        KeepListOneLine(
            keepList = keepList,
            keepListLazyListState = keepListLazyListState
        )
    }
}

@Composable
fun KeepListOneLine(
    modifier: Modifier = Modifier,
    keepList: List<Keep>,
    keepListLazyListState: LazyListState
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
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
