package com.example.keepmemo.alarm.plugin

import com.example.keepmemo.alarm.service.PluginAlarmData
import com.example.keepmemo.alarm.service.TargetVolume
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

interface AlertPlugin {
    fun go(
        alarm: PluginAlarmData,
        prealarm: Boolean,
        targetVolume: Flow<TargetVolume>
    ): Job
}
