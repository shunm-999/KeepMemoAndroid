package com.example.keepmemo.alarm.interfaces

import com.example.keepmemo.BuildConfig

object Intents {
    /** Broadcasted when an alarm fires.  */
    const val ALARM_ALERT_ACTION: String = "${BuildConfig.APPLICATION_ID}.ALARM_ALERT"

    /** Broadcasted when an alarm fires.  */
    const val ALARM_PREALARM_ACTION: String = "${BuildConfig.APPLICATION_ID}.ALARM_PREALARM_ACTION"

    /** Broadcasted when alarm is snoozed.  */
    const val ALARM_SNOOZE_ACTION: String = "${BuildConfig.APPLICATION_ID}.ALARM_SNOOZE"

    /** Broadcasted when alarm is snoozed.  */
    const val ACTION_CANCEL_SNOOZE: String = "${BuildConfig.APPLICATION_ID}.ACTION_CANCEL_SNOOZE"

    /** Broadcasted when alarm is dismissed.  */
    const val ALARM_DISMISS_ACTION: String = "${BuildConfig.APPLICATION_ID}.ALARM_DISMISS"

    /** Broadcasted when alarm is scheduled  */
    const val ACTION_SOUND_EXPIRED: String = "${BuildConfig.APPLICATION_ID}.ACTION_SOUND_EXPIRED"

    /** Key of the AlarmCore attached as a parceble extra  */
    const val EXTRA_ID = "intent.extra.alarm"

    const val ACTION_MUTE: String = "${BuildConfig.APPLICATION_ID}.ACTION_MUTE"

    const val ACTION_DEMUTE: String = "${BuildConfig.APPLICATION_ID}.ACTION_DEMUTE"

    const val ALARM_SHOW_SKIP: String = "${BuildConfig.APPLICATION_ID}.ALARM_SHOW_SKIP"

    const val ALARM_REMOVE_SKIP: String = "${BuildConfig.APPLICATION_ID}.ALARM_REMOVE_SKIP"
}
