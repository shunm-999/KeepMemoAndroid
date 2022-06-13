package com.example.keepmemo.domain

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.keepmemo.MainActivity
import com.example.keepmemo.model.Alarm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.util.Calendar
import javax.inject.Inject

class AlarmUseCase @Inject constructor() {

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

    // TODO アラームの設定仮置き
    fun setAlarmClock(context: Context) {
        val manager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis() + 5000L
        }
        val alarmTime = calendar.timeInMillis

        val alarmClockInfo = AlarmManager.AlarmClockInfo(
            alarmTime,
            null
        )
        manager.setAlarmClock(alarmClockInfo, pendingIntent)
    }
}
