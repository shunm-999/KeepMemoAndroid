package com.example.keepmemo.alarm.plugin

import com.example.keepmemo.R
import com.example.keepmemo.alarm.model.Alarmtone
import com.example.keepmemo.alarm.service.PluginAlarmData
import com.example.keepmemo.alarm.service.TargetVolume
import com.example.keepmemo.util.ktx.intervalFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

class PlayerPlugin(
    private val playerFactory: () -> Player,
    private val preAlarmVolume: Flow<Int>,
    private val fadeInTimeInMillis: Int,
    private val inCall: Flow<Boolean>
) : AlertPlugin, CoroutineScope {

    companion object {
        private const val FAST_FADE_IN_TIME = 5000
        private const val FADE_IN_STEPS = 100
        private const val IN_CALL_VOLUME = 0.125f
        private const val SILENT = 0f
    }

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + Dispatchers.IO

    private var player: Player? = null
    private var job: Job? = null

    override fun go(
        alarm: PluginAlarmData,
        prealarm: Boolean,
        targetVolume: Flow<TargetVolume>
    ): Job {
        job?.cancel()
        player = playerFactory()

        val _job = launch {
            launch {
                inCall.collect { inCall ->
                    if (inCall) {
                        playInCallAlarm()
                    } else {
                        playAlarm(alarm)
                    }
                }
            }
            launch {
                targetVolume.flatMapLatest { volume ->
                    when (volume) {
                        TargetVolume.MUTED -> {
                            { 0f }.asFlow()
                        }
                        TargetVolume.FADED_IN -> fadeInSlow(prealarm)
                        TargetVolume.FADED_IN_FAST -> fadeIn(FAST_FADE_IN_TIME, prealarm)
                    }
                }.collect { currentVolume ->
                    player?.setPerceivedVolume(currentVolume)
                }
            }
        }
        job = _job
        return _job
    }

    private fun fadeInSlow(preAlarm: Boolean): Flow<Float> {
        return fadeIn(fadeInTimeInMillis, preAlarm)
    }

    private fun playAlarm(alarm: PluginAlarmData) {
        if (alarm.alarmtone is Alarmtone.Silent) {
            return
        }

        player?.run {
            try {
                setPerceivedVolume(0f)
                setDataSource(alarm.alarmtone)
                startAlarm()
            } catch (e: Exception) {
                Timber.w("Using the fallback ringtone")
                reset()
                setDataSourceFromResource(R.raw.fallbackring)
                startAlarm()
            }
        }
    }

    private fun playInCallAlarm() {
        Timber.d("Using the in-call alarm")
        player?.run {
            reset()
            setDataSourceFromResource(R.raw.in_call_alarm)
            startAlarm()
        }
    }

    private fun observeVolume(preAlarm: Boolean): Flow<Float> {
        val maxVolume = 11
        return if (preAlarm) {
            preAlarmVolume.map {
                it.plus(1) // 0 is 1
                    .coerceAtMost(maxVolume)
                    .toFloat()
                    .div(maxVolume)
                    .div(2)
            }
        } else {
            flow {
                emit(1f)
            }
        }
    }

    private fun fadeIn(time: Int, preAlarm: Boolean): Flow<Float> {
        val fadeInTime: Long = time.toLong()
        val fadeInStep: Long = fadeInTime / FADE_IN_STEPS

        val fadeIn = intervalFlow(fadeInStep, TimeUnit.MILLISECONDS, takeWhile = { count ->
            count * fadeInStep <= fadeInTime
        }).map {
            it * fadeInStep
        }.map { elapsed ->
            elapsed.toFloat() / fadeInTime
        }.map { fraction ->
            fraction.squared()
        }.onCompletion { Timber.d("Completed fade-in in $time milliseconds") }

        return combine(
            observeVolume(preAlarm),
            fadeIn
        ) { targetVolume, fadePercentage ->
            fadePercentage * targetVolume
        }
    }

    private fun stopAndCleanUp() {
        Timber.d("stopping media player")
        try {
            player?.stop()
        } finally {
            player = null
        }
    }

    private fun Float.squared() = this * this
}
