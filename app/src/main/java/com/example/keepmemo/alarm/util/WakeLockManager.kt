package com.example.keepmemo.alarm.util

import android.content.Intent
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import timber.log.Timber
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

interface Wakelocks {
    fun acquireServiceLock()

    fun releaseServiceLock()
}

class WakeLockManager @Inject constructor(
    private val powerManager: PowerManager
) : Wakelocks {

    companion object {
        const val COUNT = "WakeLockManager.COUNT"
    }

    private val wakelockCounter = AtomicInteger(0)
    private val wakeLockIds = CopyOnWriteArrayList<Int>()
    private val serviceWakelock =
        powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "SimpleAlarmClock:AlertServiceWrapper"
        )
    private val transitionWakelock =
        powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "SimpleAlarmClock:AlertServicePusher"
        )

    override fun acquireServiceLock() {
        Timber.d("Acquired service wakelock")
        serviceWakelock.acquire(60 * 60_000)
    }

    override fun releaseServiceLock() {
        if (serviceWakelock.isHeld) {
            Timber.d("Released service wakelock")
            serviceWakelock.release()
        }
    }

    /**
     * Acquires a partial [WakeLock], stores it internally and puts the tag into the [Intent]. To be
     * used with [WakeLockManager.releaseTransitionWakeLock]
     */
    fun acquireTransitionWakeLock(intent: Intent): Intent {
        transitionWakelock.acquire(60 * 1000)
        wakelockCounter.incrementAndGet().also { count ->
            Timber.d("Acquired $transitionWakelock #$count")
            wakeLockIds.add(count)
            return intent.putExtra(COUNT, count)
        }
    }

    /**
     * Releases a partial [WakeLock] with a tag contained in the given [Intent]
     *
     * @param intent
     */
    fun releaseTransitionWakeLock(intent: Intent) {
        val count = intent.getIntExtra(COUNT, -1)
        val wasRemoved = wakeLockIds.remove(count)
        if (wasRemoved && transitionWakelock.isHeld) {
            Timber.d("Released $transitionWakelock #$count")
            transitionWakelock.release()
        }
    }
}
