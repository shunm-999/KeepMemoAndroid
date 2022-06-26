package com.example.keepmemo.alarm.util

import android.content.Context
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext

interface VibratorUtilInterface {

    fun vibrate(vibratePattern: LongArray, amplitude: Int)

    fun cancel()
}

class PreOreoVibratorUtil(
    @ApplicationContext context: Context
) : VibratorUtilInterface {
    private val vibrator: Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    override fun vibrate(vibratePattern: LongArray, amplitude: Int) {
        vibrator.vibrate(vibratePattern, 0)
    }

    override fun cancel() {
        vibrator.cancel()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
class OreoVibratorUtil(
    @ApplicationContext context: Context
) : VibratorUtilInterface {
    private val vibrator: Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    override fun vibrate(vibratePattern: LongArray, amplitude: Int) {
        val vibrationEffect = VibrationEffect.createWaveform(
            vibratePattern,
            intArrayOf(0, amplitude),
            0
        )
        vibrator.vibrate(vibrationEffect)
    }

    override fun cancel() {
        vibrator.cancel()
    }
}

@RequiresApi(Build.VERSION_CODES.S)
class SVibratorUtil(
    @ApplicationContext context: Context
) : VibratorUtilInterface {
    private val vibratorManager: VibratorManager =
        context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager

    override fun vibrate(vibratePattern: LongArray, amplitude: Int) {
        val vibrationEffect = VibrationEffect.createWaveform(
            vibratePattern,
            intArrayOf(0, amplitude),
            0
        )
        val combinedVibration = CombinedVibration.createParallel(vibrationEffect)
        vibratorManager.vibrate(combinedVibration)
    }

    override fun cancel() {
        vibratorManager.cancel()
    }
}
