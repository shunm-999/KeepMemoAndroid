package com.example.keepmemo.alarm.service

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.example.keepmemo.alarm.Event
import com.example.keepmemo.alarm.interfaces.Intents
import com.example.keepmemo.alarm.util.WakeLockManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AlertServicePusher @Inject constructor(
    @ApplicationContext private val context: Context,
    private val wakeLockManager: WakeLockManager
) {

    fun startService(event: Event) {
        when (event) {
            is Event.AlarmEvent ->
                Intent(Intents.ALARM_ALERT_ACTION).apply { putExtra(Intents.EXTRA_ID, event.id) }
            is Event.PrealarmEvent ->
                Intent(Intents.ALARM_PREALARM_ACTION).apply { putExtra(Intents.EXTRA_ID, event.id) }
            is Event.DismissEvent ->
                Intent(Intents.ALARM_DISMISS_ACTION).apply { putExtra(Intents.EXTRA_ID, event.id) }
            is Event.MuteEvent -> Intent(Intents.ACTION_MUTE)
            is Event.DemuteEvent -> Intent(Intents.ACTION_DEMUTE)
            is Event.SnoozedEvent -> null
            is Event.Autosilenced -> null
            is Event.CancelSnoozedEvent -> null
            is Event.ShowSkip -> null
            is Event.HideSkip -> null
            is Event.NullEvent -> throw RuntimeException("NullEvent")
            else -> {
                throw RuntimeException("NullEvent")
            }
        }?.apply {
            setClass(context, AlertServiceWrapper::class.java)
        }?.let { intent ->
            val transitionIntent = wakeLockManager.acquireTransitionWakeLock(intent)
            ContextCompat.startForegroundService(context, transitionIntent)
        }
    }

    // TODO 状態を監視する
    // init {
    //     store.events
    //         .mapNotNull {
    //             when (it) {
    //                 is Event.AlarmEvent ->
    //                     Intent(Intents.ALARM_ALERT_ACTION).apply { putExtra(Intents.EXTRA_ID, it.id) }
    //                 is Event.PrealarmEvent ->
    //                     Intent(Intents.ALARM_PREALARM_ACTION).apply { putExtra(Intents.EXTRA_ID, it.id) }
    //                 is Event.DismissEvent ->
    //                     Intent(Intents.ALARM_DISMISS_ACTION).apply { putExtra(Intents.EXTRA_ID, it.id) }
    //                 is Event.MuteEvent -> Intent(Intents.ACTION_MUTE)
    //                 is Event.DemuteEvent -> Intent(Intents.ACTION_DEMUTE)
    //                 is Event.SnoozedEvent -> null
    //                 is Event.Autosilenced -> null
    //                 is Event.CancelSnoozedEvent -> null
    //                 is Event.ShowSkip -> null
    //                 is Event.HideSkip -> null
    //                 is Event.NullEvent -> throw RuntimeException("NullEvent")
    //             }?.apply { setClass(context, AlertServiceWrapper::class.java) }
    //         }
    //         .subscribeForever { intent ->
    //             wm.acquireTransitionWakeLock(intent)
    //             oreo { context.startForegroundService(intent) }
    //             preOreo { context.startService(intent) }
    //             logger.debug { "pushed intent ${intent.action} to AlertServiceWrapper" }
    //         }
    // }
}
