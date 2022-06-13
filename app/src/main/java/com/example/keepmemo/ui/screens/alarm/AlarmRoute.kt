package com.example.keepmemo.ui.screens.alarm

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@Composable
fun AlarmRoute(
    alarmViewModel: AlarmViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
) {
    val context = LocalContext.current
    val uiState by alarmViewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    AlarmRoute(
        uiState = uiState,
        onCheckedChange = { (id, checked) ->
            coroutineScope.launch {
                if (checked) {
                    alarmViewModel.enableAlarm(id)
                    alarmViewModel.setAlarmClock(context, id)
                } else {
                    alarmViewModel.disableAlarm(id)
                }
            }
        },
        openDrawer = openDrawer
    )
}

@Composable
private fun AlarmRoute(
    uiState: AlarmUiState,
    onCheckedChange: (Pair<Long, Boolean>) -> Unit,
    openDrawer: () -> Unit
) {
    val listLazyListState = rememberLazyListState()
    val scaffoldState = rememberScaffoldState()

    AlarmScreen(
        alarmList = uiState.alarmList,
        onCheckedChange = onCheckedChange,
        scaffoldState = scaffoldState,
        listLazyListState = listLazyListState,
        openDrawer = openDrawer
    )
}
