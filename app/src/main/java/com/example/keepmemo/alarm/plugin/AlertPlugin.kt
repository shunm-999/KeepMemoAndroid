package com.example.keepmemo.alarm.plugin

import com.example.keepmemo.alarm.PluginAlarmData
import com.example.keepmemo.alarm.TargetVolume
import kotlinx.coroutines.flow.Flow

interface AlertPlugin {
    suspend fun go(
        alarm: PluginAlarmData,
        prealarm: Boolean,
        targetVolume: Flow<TargetVolume>
    )
}
