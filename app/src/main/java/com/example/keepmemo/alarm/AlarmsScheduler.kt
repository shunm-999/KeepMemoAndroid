package com.example.keepmemo.alarm

import com.example.keepmemo.BuildConfig
import java.text.SimpleDateFormat
import java.util.Locale

class AlarmsScheduler {

    companion object {
        val DATE_FORMAT: SimpleDateFormat = SimpleDateFormat("dd-MM-yy HH:mm:ss", Locale.GERMANY)
        const val ACTION_FIRED = BuildConfig.APPLICATION_ID + ".ACTION_FIRED"
        const val ACTION_INEXACT_FIRED = BuildConfig.APPLICATION_ID + ".ACTION_INEXACT_FIRED"
        const val EXTRA_ID = "intent.extra.alarm"
        const val EXTRA_TYPE = "intent.extra.type"
    }
}
