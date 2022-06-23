package com.example.keepmemo.domain

import com.example.keepmemo.alarm.AlarmSetterInterface
import java.util.Calendar
import javax.inject.Inject

class SetupAlarmUseCase @Inject constructor(
    private val alarmSetter: AlarmSetterInterface
) {
    // TODO 仮置き
    fun setAlarmClock() {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis() + 5000L
        }
        alarmSetter.setUpRTCAlarm(
            id = 1,
            typeName = "test",
            calendar = calendar
        )
    }
}
