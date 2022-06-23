package com.example.keepmemo.alarm

import android.annotation.TargetApi
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.keepmemo.util.pendingIntentUpdateCurrentFlag
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.util.Calendar
import javax.inject.Inject

interface AlarmSetterInterface {

    fun removeRTCAlarm()

    fun setUpRTCAlarm(id: Int, typeName: String, calendar: Calendar)

    fun fireNow(id: Int, typeName: String)

    fun removeInexactAlarm(id: Int)

    fun setInexactAlarm(id: Int, calendar: Calendar)
}

class AlarmSetterImpl(
    @ApplicationContext private val context: Context,
    private val alarmManager: AlarmManager,
    private val setAlarmStrategy: ISetAlarmStrategy
) : AlarmSetterInterface {

    override fun removeRTCAlarm() {
        Timber.d("Removed all alarms")
        val pendingAlarm =
            PendingIntent.getBroadcast(
                context,
                pendingAlarmRequestCode,
                Intent(ACTION_FIRED).apply {
                    // must be here, otherwise replace does not work
                    setClass(context, AlarmsReceiver::class.java)
                },
                pendingIntentUpdateCurrentFlag()
            )
        alarmManager.cancel(pendingAlarm)
    }

    override fun setUpRTCAlarm(id: Int, typeName: String, calendar: Calendar) {
        Timber.d("Set $id ($typeName) on ${AlarmsScheduler.DATE_FORMAT.format(calendar.time)}")
        val pendingAlarm =
            Intent(ACTION_FIRED)
                .apply {
                    setClass(context, AlarmsReceiver::class.java)
                    putExtra(EXTRA_ID, id)
                    putExtra(EXTRA_TYPE, typeName)
                }
                .let {
                    PendingIntent.getBroadcast(
                        context, pendingAlarmRequestCode, it, pendingIntentUpdateCurrentFlag()
                    )
                }

        setAlarmStrategy.setRTCAlarm(calendar, pendingAlarm)
    }

    override fun fireNow(id: Int, typeName: String) {
        val intent =
            Intent(ACTION_FIRED).apply {
                setClass(context, AlarmsReceiver::class.java)
                putExtra(EXTRA_ID, id)
                putExtra(EXTRA_TYPE, typeName)
            }
        context.sendBroadcast(intent)
    }

    override fun setInexactAlarm(id: Int, calendar: Calendar) {
        Timber.d("setInexactAlarm id: $id on ${AlarmsScheduler.DATE_FORMAT.format(calendar.time)}")
        val pendingAlarm =
            Intent(ACTION_INEXACT_FIRED)
                .apply {
                    setClass(context, AlarmsReceiver::class.java)
                    putExtra(EXTRA_ID, id)
                }
                .let {
                    PendingIntent.getBroadcast(context, id, it, pendingIntentUpdateCurrentFlag())
                }

        setAlarmStrategy.setInexactAlarm(calendar, pendingAlarm)
    }

    override fun removeInexactAlarm(id: Int) {
        Timber.d("removeInexactAlarm id: $id")
        val pendingAlarm =
            PendingIntent.getBroadcast(
                context,
                id,
                Intent(ACTION_INEXACT_FIRED).apply {
                    // must be here, otherwise replace does not work
                    setClass(context, AlarmsReceiver::class.java)
                },
                pendingIntentUpdateCurrentFlag()
            )
        alarmManager.cancel(pendingAlarm)
    }
}

interface ISetAlarmStrategy {
    fun setRTCAlarm(calendar: Calendar, pendingIntent: PendingIntent)
    fun setInexactAlarm(calendar: Calendar, pendingIntent: PendingIntent) {
        setRTCAlarm(calendar, pendingIntent)
    }
}

class KitKatSetter @Inject constructor(private val am: AlarmManager) : ISetAlarmStrategy {
    override fun setRTCAlarm(calendar: Calendar, pendingIntent: PendingIntent) {
        am.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    override fun setInexactAlarm(calendar: Calendar, pendingIntent: PendingIntent) {
        am.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}

@TargetApi(Build.VERSION_CODES.M)
class MarshmallowSetter @Inject constructor(private val am: AlarmManager) : ISetAlarmStrategy {
    override fun setRTCAlarm(calendar: Calendar, pendingIntent: PendingIntent) {
        am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}

/** 8.0 */
@TargetApi(Build.VERSION_CODES.O)
class OreoSetter @Inject constructor(
    @ApplicationContext private val context: Context,
    private val am: AlarmManager
) : ISetAlarmStrategy {
    override fun setRTCAlarm(calendar: Calendar, pendingIntent: PendingIntent) {
        // 通知タップ時のintent
        val pendingShowList =
            PendingIntent.getActivity(
                context,
                100500,
                Intent(context, AlarmsReceiver::class.java),
                pendingIntentUpdateCurrentFlag()
            )
        am.setAlarmClock(
            AlarmManager.AlarmClockInfo(calendar.timeInMillis, pendingShowList), pendingIntent
        )
    }

    override fun setInexactAlarm(calendar: Calendar, pendingIntent: PendingIntent) {
        am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}

private const val pendingAlarmRequestCode = 0

const val ACTION_FIRED = AlarmsScheduler.ACTION_FIRED
const val ACTION_INEXACT_FIRED = AlarmsScheduler.ACTION_INEXACT_FIRED
const val EXTRA_ID = AlarmsScheduler.EXTRA_ID
const val EXTRA_TYPE = AlarmsScheduler.EXTRA_TYPE
