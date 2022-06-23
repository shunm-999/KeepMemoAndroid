package com.example.keepmemo.alarm.plugin

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.keepmemo.MainActivity
import com.example.keepmemo.R
import com.example.keepmemo.alarm.EnclosingService
import com.example.keepmemo.alarm.PluginAlarmData
import com.example.keepmemo.alarm.interfaces.Intents
import com.example.keepmemo.alarm.interfaces.PresentationToModelIntents
import com.example.keepmemo.util.CHANNEL_ID_HIGH_PRIO
import com.example.keepmemo.util.DeviceUtil
import com.example.keepmemo.util.notificationBuilder
import com.example.keepmemo.util.pendingIntentUpdateCurrentFlag
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext

class NotificationsPlugin @AssistedInject constructor(
    @ApplicationContext private val context: Context,
    private val notificationManager: NotificationManager,
    @Assisted private val enclosingService: EnclosingService
) {

    fun show(alarm: PluginAlarmData, index: Int, startForeground: Boolean) {

        val pendingNotify =
            Intent(context, MainActivity::class.java).apply {
                action = MainActivity.ACTION_FULLSCREEN
                putExtra(Intents.EXTRA_ID, alarm.id)
            }.let { intent ->
                PendingIntent.getActivity(
                    context,
                    alarm.id,
                    intent,
                    pendingIntentUpdateCurrentFlag()
                )
            }
        val pendingSnooze =
            PresentationToModelIntents.createPendingIntent(
                context, PresentationToModelIntents.ACTION_REQUEST_SNOOZE, alarm.id
            )
        val pendingDismiss =
            PresentationToModelIntents.createPendingIntent(
                context, PresentationToModelIntents.ACTION_REQUEST_DISMISS, alarm.id
            )

        val notification =
            context.notificationBuilder(CHANNEL_ID_HIGH_PRIO) {
                setContentTitle(alarm.label)
                setContentText(context.getString(R.string.alarm_notify_text))
                setSmallIcon(R.drawable.stat_notify_alarm)
                priority = NotificationCompat.PRIORITY_HIGH
                setCategory(NotificationCompat.CATEGORY_ALARM)
                // setFullScreenIntent to show the user AlarmAlert dialog at the same time
                // when the Notification Bar was created.
                setFullScreenIntent(pendingNotify, true)
                // setContentIntent to show the user AlarmAlert dialog
                // when he will click on the Notification Bar.
                setContentIntent(pendingNotify)
                setOngoing(true)
                addAction(
                    R.drawable.ic_action_snooze,
                    context.getString(R.string.alarm_alert_snooze_text),
                    pendingSnooze
                )
                addAction(
                    R.drawable.ic_action_dismiss,
                    context.getString(R.string.alarm_alert_dismiss_text),
                    pendingDismiss
                )
                setDefaults(Notification.DEFAULT_LIGHTS)
            }

        if (startForeground && DeviceUtil.isOreoOver()) {
            // Oreo以上の場合は、コールバックで通知
            enclosingService.startForeground(index + OFFSET, notification)
        } else {
            notificationManager.notify(index + OFFSET, notification)
        }
    }

    fun cancel(index: Int) {
        notificationManager.cancel(index + OFFSET)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            enclosingService: EnclosingService
        ): NotificationsPlugin
    }

    companion object {
        private const val OFFSET = 100000
    }
}
