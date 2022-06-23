package com.example.keepmemo.alarm.interfaces

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.keepmemo.BuildConfig
import com.example.keepmemo.alarm.AlarmsReceiver
import com.example.keepmemo.util.pendingIntentUpdateCurrentFlag

object PresentationToModelIntents {

    val ACTION_REQUEST_SNOOZE: String =
        "${BuildConfig.APPLICATION_ID}.model.interfaces.ServiceIntents.ACTION_REQUEST_SNOOZE"
    val ACTION_REQUEST_DISMISS: String =
        "${BuildConfig.APPLICATION_ID}.model.interfaces.ServiceIntents.ACTION_REQUEST_DISMISS"
    val ACTION_REQUEST_SKIP: String =
        "${BuildConfig.APPLICATION_ID}.model.interfaces.ServiceIntents.ACTION_REQUEST_SKIP"

    fun createPendingIntent(context: Context, action: String, id: Int): PendingIntent? {
        return Intent(action).apply {
            setClass(context, AlarmsReceiver::class.java)
            putExtra(Intents.EXTRA_ID, id)
        }.let { intent ->
            PendingIntent.getBroadcast(
                context,
                id,
                intent,
                pendingIntentUpdateCurrentFlag()
            )
        }
    }
}
