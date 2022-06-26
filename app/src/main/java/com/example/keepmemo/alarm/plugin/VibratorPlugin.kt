package com.example.keepmemo.alarm.plugin

import android.os.Build
import android.os.VibrationEffect
import com.example.keepmemo.alarm.service.PluginAlarmData
import com.example.keepmemo.alarm.service.TargetVolume
import com.example.keepmemo.alarm.util.VibratorUtilInterface
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class VibratorPlugin @AssistedInject constructor(
    private val vibratorUtil: VibratorUtilInterface,
    @Assisted private val fadeInTimeInMillis: Int,
    @Assisted private val vibrateEnabled: Flow<Boolean>
) : AlertPlugin, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + Dispatchers.IO

    private var job: Job? = null

    private val vibratePattern: LongArray = longArrayOf(500, 500)
    private val defaultAmplitude: Int
        get() =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                VibrationEffect.DEFAULT_AMPLITUDE
            } else {
                -1
            }

    override fun go(
        alarm: PluginAlarmData,
        prealarm: Boolean,
        targetVolume: Flow<TargetVolume>
    ): Job {
        job?.cancel()
        val _job = launch {
            combine(
                vibrateEnabled,
                targetVolume
            ) { vibrateEnabled, volume ->
                if (vibrateEnabled) volume else TargetVolume.MUTED
            }.distinctUntilChanged()
                .flatMapLatest { volume ->
                    when (volume) {
                        TargetVolume.MUTED -> {
                            { 0 }.asFlow()
                        }
                        TargetVolume.FADED_IN -> fadeInSlow(prealarm)
                        TargetVolume.FADED_IN_FAST -> {
                            { 255 }.asFlow()
                        }
                    }
                }.distinctUntilChanged()
                .collect { amplitude ->
                    if (amplitude > 0) {
                        vibratorUtil.vibrate(vibratePattern, amplitude)
                    } else {
                        vibratorUtil.cancel()
                    }
                }
        }
        job = _job
        return _job
    }

    private fun fadeInSlow(prealarm: Boolean): Flow<Int> {
        return if (prealarm) {
            { 0 }.asFlow()
        } else {
            flow {
                emit(0)
                delay(fadeInTimeInMillis.toLong())
                emit(defaultAmplitude)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            fadeInTimeInMillis: Int,
            vibrateEnabled: Flow<Boolean>
        ): VibratorPlugin
    }
}
