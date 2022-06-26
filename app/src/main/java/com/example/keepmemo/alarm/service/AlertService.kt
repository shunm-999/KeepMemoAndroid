package com.example.keepmemo.alarm.service

import android.app.Notification
import com.example.keepmemo.alarm.interfaces.Intents
import com.example.keepmemo.alarm.model.Alarmtone
import com.example.keepmemo.alarm.plugin.AlertPlugin
import com.example.keepmemo.alarm.plugin.NotificationsPlugin
import com.example.keepmemo.alarm.util.Wakelocks
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Calendar

data class PluginAlarmData(val id: Int, val alarmtone: Alarmtone, val label: String)

enum class TargetVolume {
    MUTED,
    FADED_IN,
    FADED_IN_FAST
}

sealed class Event {
    data class NullEvent(val actions: String = "null") : Event()
    data class AlarmEvent(
        val id: Int,
        val actions: String = Intents.ALARM_ALERT_ACTION
    ) : Event()

    data class PrealarmEvent(
        val id: Int,
        val actions: String = Intents.ALARM_PREALARM_ACTION
    ) : Event()

    data class DismissEvent(
        val id: Int,
        val actions: String = Intents.ALARM_DISMISS_ACTION
    ) : Event()

    data class SnoozedEvent(
        val id: Int,
        val calendar: Calendar,
        val actions: String = Intents.ALARM_SNOOZE_ACTION
    ) : Event()

    data class ShowSkip(
        val id: Int,
        val actions: String = Intents.ALARM_SHOW_SKIP
    ) : Event()

    data class HideSkip(
        val id: Int,
        val actions: String = Intents.ALARM_REMOVE_SKIP
    ) : Event()

    data class CancelSnoozedEvent(
        val id: Int,
        val actions: String = Intents.ACTION_CANCEL_SNOOZE
    ) : Event()

    data class Autosilenced(
        val id: Int,
        val actions: String = Intents.ACTION_SOUND_EXPIRED
    ) : Event()

    data class MuteEvent(val actions: String = Intents.ACTION_MUTE) : Event()
    data class DemuteEvent(val actions: String = Intents.ACTION_DEMUTE) : Event()
}

interface EnclosingService {
    fun handleUnwantedEvent()
    fun stopSelf()
    fun startForeground(id: Int, notification: Notification)
}

class AlertService @AssistedInject constructor(
    private val wakelocks: Wakelocks,
    @Assisted private val inCall: Flow<Boolean>,
    @Assisted private val notificationsPlugin: NotificationsPlugin,
    @Assisted private val plugins: List<AlertPlugin>,
    @Assisted private val enclosingService: EnclosingService
) {

    private enum class Type {
        NORMAL,
        PREALARM
    }

    private var nowShowing = emptyList<Int>()

    init {
        wakelocks.acquireServiceLock()
        // TODO 仮置き
        showNotifications(emptyMap())
        plugins.map {
            it.go(
                PluginAlarmData(-1, Alarmtone.Default, "label"),
                prealarm = false,
                targetVolume = flow {
                    emit(TargetVolume.FADED_IN_FAST)
                }
            )
        }
    }

    fun onStartCommand(event: Event): Boolean {
        // TODO 仮置き
        return false
    }

    // TODO 仮置き
    private fun showNotifications(activeAlert: Map<Int, Type>) {
        notificationsPlugin.show(
            alarm = PluginAlarmData(
                id = 1,
                alarmtone = Alarmtone.Silent,
                label = "label"
            ),
            index = 0,
            startForeground = true
        )
    }

    fun onDestroy() {
        wakelocks.releaseServiceLock()
    }

    @AssistedFactory
    interface Factory {
        fun create(
            inCall: Flow<Boolean>,
            notificationsPlugin: NotificationsPlugin,
            plugins: List<AlertPlugin>,
            enclosingService: EnclosingService
        ): AlertService
    }
}
