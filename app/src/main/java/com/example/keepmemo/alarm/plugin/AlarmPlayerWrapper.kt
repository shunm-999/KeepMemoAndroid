package com.example.keepmemo.alarm.plugin

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import com.example.keepmemo.alarm.model.Alarmtone
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

interface Player {
    fun startAlarm()
    fun setDataSourceFromResource(res: Int)
    fun setPerceivedVolume(perceived: Float)

    /** Stops alarm audio */
    fun stop()

    fun reset()
    fun setDataSource(alarmtone: Alarmtone)
}

class AlarmPlayerWrapper @Inject constructor(
    @ApplicationContext private val context: Context
) : Player {

    private var player: MediaPlayer? =
        MediaPlayer().apply {
            setOnErrorListener { mp, what, extra ->
                Timber.tag("AlarmPlayerWrapper").e("what $what extra $extra")
                mp.stop()
                mp.release()
                player = null
                true
            }
        }

    override fun setDataSource(alarmtone: Alarmtone) {
        val uri = when (alarmtone) {
            is Alarmtone.Silent -> throw RuntimeException("alarm is silent")
            is Alarmtone.Default -> RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            is Alarmtone.Sound -> Uri.parse(alarmtone.uriString)
        }
        player?.setDataSource(context, uri)
    }

    override fun startAlarm() {
        player?.runCatching {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            isLooping = true
            prepare()
            start()
        }
    }

    override fun setDataSourceFromResource(res: Int) {
        context.resources.openRawResourceFd(res)?.run {
            player?.setDataSource(fileDescriptor, startOffset, length)
            close()
        }
    }

    override fun setPerceivedVolume(perceived: Float) {
        val volume = perceived.squared()
        player?.setVolume(volume, volume)
    }

    /** Stops alarm audio */
    override fun stop() {
        try {
            player?.run {
                if (isPlaying) {
                    stop()
                }
                release()
            }
        } finally {
            player = null
        }
    }

    override fun reset() {
        player?.reset()
    }

    private fun Float.squared() = this * this
}
