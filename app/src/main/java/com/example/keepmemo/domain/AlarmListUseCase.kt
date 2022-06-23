package com.example.keepmemo.domain

import com.example.keepmemo.model.Alarm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class AlarmListUseCase @Inject constructor() {

    // TODO 仮置き
    private val alarmListFlow = MutableStateFlow(
        listOf(
            Alarm(id = 1, hour = 6, minute = 30, enabled = false),
            Alarm(id = 2, hour = 13, minute = 40, enabled = true)
        )
    )

    suspend fun invokeAlarmList(): Flow<List<Alarm>> {
        return alarmListFlow
    }

    suspend fun invokeEnableAlarm(id: Long) {
        alarmListFlow.update {
            mutableListOf<Alarm>().apply {
                it.forEach {
                    add(
                        if (it.id == id) {
                            it.copy(enabled = true)
                        } else {
                            it.copy()
                        }
                    )
                }
            }
        }
    }

    suspend fun invokeDisableAlarm(id: Long) {
        alarmListFlow.update {
            mutableListOf<Alarm>().apply {
                it.forEach {
                    add(
                        if (it.id == id) {
                            it.copy(enabled = false)
                        } else {
                            it.copy()
                        }
                    )
                }
            }
        }
    }
}
