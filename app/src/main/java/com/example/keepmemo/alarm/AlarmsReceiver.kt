package com.example.keepmemo.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.keepmemo.alarm.service.AlertServicePusher
import com.example.keepmemo.alarm.service.Event
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmsReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alertServicePusher: AlertServicePusher

    // TODO アクションに応じたアラームの状態管理の実装
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) {
            return
        }
        // TODO 仮置き
        alertServicePusher.startService(Event.AlarmEvent(id = 1))
    }
}
