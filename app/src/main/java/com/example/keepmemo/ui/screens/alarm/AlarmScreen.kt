package com.example.keepmemo.ui.screens.alarm

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FabPosition
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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.keepmemo.R
import com.example.keepmemo.model.Alarm
import com.example.keepmemo.ui.component.KeepMemoSnackbarHost
import com.example.keepmemo.ui.ktx.isScrolled
import com.example.keepmemo.ui.theme.KeepMemoTheme

@Composable
fun AlarmScreen(
    alarmList: List<Alarm>,
    onCheckedChange: (Pair<Long, Boolean>) -> Unit,
    scaffoldState: ScaffoldState,
    listLazyListState: LazyListState,
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { KeepMemoSnackbarHost(hostState = it) },
        topBar = {
            AlarmTopAppBar(
                openDrawer = openDrawer,
                elevation = if (!listLazyListState.isScrolled) 0.dp else 4.dp
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
            }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        modifier = modifier
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding)
        AlarmScreenContent(
            alarmList = alarmList,
            onCheckedChange = onCheckedChange,
            listLazyListState = listLazyListState,
            modifier = contentModifier
        )
    }
}

@Composable
fun AlarmScreenContent(
    alarmList: List<Alarm>,
    onCheckedChange: (Pair<Long, Boolean>) -> Unit,
    listLazyListState: LazyListState,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier) {
        AlarmListOneLine(
            alarmList = alarmList,
            onCheckedChange = onCheckedChange,
            listLazyListState = listLazyListState
        )
    }
}

@Composable
fun AlarmListOneLine(
    alarmList: List<Alarm>,
    onCheckedChange: (Pair<Long, Boolean>) -> Unit,
    listLazyListState: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = listLazyListState
    ) {
        items(alarmList) { alarm ->
            AlarmCard(
                hour = alarm.hour,
                minute = alarm.minute,
                isChecked = alarm.enabled,
                onCheckedChange = { checked ->
                    onCheckedChange(alarm.id to checked)
                }
            )
        }
    }
}

/**
 * TopAppBar for the Home screen
 */
@Composable
private fun AlarmTopAppBar(
    elevation: Dp,
    openDrawer: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.alarm_appbar_title))
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
        backgroundColor = MaterialTheme.colors.surface,
        elevation = elevation
    )
}

@Preview("AlarmScreen")
@Preview("AlarmScreen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AlarmScreenPreview() {
    KeepMemoTheme {
        AlarmScreen(
            alarmList = listOf(
                Alarm(id = 1, hour = 6, minute = 30, enabled = false),
                Alarm(id = 2, hour = 13, minute = 40, enabled = true)
            ),
            onCheckedChange = {},
            scaffoldState = rememberScaffoldState(),
            listLazyListState = rememberLazyListState(),
            openDrawer = { }
        )
    }
}
