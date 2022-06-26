package com.example.keepmemo.alarm.service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.telephony.TelephonyManager
import com.example.keepmemo.R
import com.example.keepmemo.alarm.interfaces.Intents
import com.example.keepmemo.alarm.plugin.AlarmPlayerWrapper
import com.example.keepmemo.alarm.plugin.NotificationsPlugin
import com.example.keepmemo.alarm.plugin.PlayerPlugin
import com.example.keepmemo.alarm.util.WakeLockManager
import com.example.keepmemo.alarm.util.observeCallState
import com.example.keepmemo.alarm.util.observePhoneState
import com.example.keepmemo.util.CHANNEL_ID
import com.example.keepmemo.util.DeviceUtil
import com.example.keepmemo.util.notificationBuilder
import com.example.keepmemo.util.oreo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class AlertServiceWrapper : Service() {

    @Inject
    lateinit var wakeLockManager: WakeLockManager

    @Inject
    lateinit var telephonyManager: TelephonyManager

    private val inCallState: Flow<Boolean> by lazy {
        if (DeviceUtil.isSOrOver()) {
            telephonyManager.observeCallState(applicationContext.mainExecutor)
        } else {
            telephonyManager.observePhoneState()
        }
    }

    @Inject
    lateinit var notificationsPluginFactory: NotificationsPlugin.Factory
    private val notificationsPlugin by lazy {
        notificationsPluginFactory.create(
            enclosingService = enclosingService
        )
    }

    // TODO 仮置き
    private val playerPlugin by lazy {
        PlayerPlugin(
            playerFactory = {
                AlarmPlayerWrapper(applicationContext)
            },
            preAlarmVolume = flow {
                emit(5)
            },
            fadeInTimeInMillis = 3000,
            inCall = inCallState
        )
    }

    // コールバックをうけとる
    private val enclosingService = object : EnclosingService {
        override fun handleUnwantedEvent() {
            oreo {
                val notification =
                    notificationBuilder(CHANNEL_ID) {
                        setContentTitle("Background")
                        setContentText("Background")
                        setSmallIcon(R.drawable.stat_notify_alarm)
                        setOngoing(true)
                    }
                this@AlertServiceWrapper.startForeground(42, notification)
            }
            this@AlertServiceWrapper.stopSelf()
        }

        override fun startForeground(id: Int, notification: Notification) {
            Timber.tag("SERVICE").d("startForeground!!! $id, $notification")
            this@AlertServiceWrapper.startForeground(id, notification)
        }

        override fun stopSelf() {
            Timber.tag("SERVICE").w("stopSelf()")
            this@AlertServiceWrapper.stopSelf()
        }
    }

    @Inject
    lateinit var alertServiceFactory: AlertService.Factory
    private val alertService: AlertService by lazy {
        alertServiceFactory.create(
            inCall = inCallState,
            notificationsPlugin = notificationsPlugin,
            playerPlugin = playerPlugin,
            enclosingService = enclosingService
        )
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        if (DeviceUtil.isOreoOver()) {
            stopForeground(true)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return if (intent == null) {
            alertService.onStartCommand(Event.NullEvent())
            START_NOT_STICKY
        } else {
            alertService.onStartCommand(
                when (intent.action) {
                    Intents.ALARM_ALERT_ACTION -> Event.AlarmEvent(
                        intent.getIntExtra(
                            Intents.EXTRA_ID,
                            -1
                        )
                    )
                    Intents.ALARM_PREALARM_ACTION ->
                        Event.PrealarmEvent(intent.getIntExtra(Intents.EXTRA_ID, -1))
                    Intents.ACTION_MUTE -> Event.MuteEvent()
                    Intents.ACTION_DEMUTE -> Event.DemuteEvent()
                    Intents.ALARM_DISMISS_ACTION ->
                        Event.DismissEvent(intent.getIntExtra(Intents.EXTRA_ID, -1))
                    else -> throw RuntimeException("Unknown action ${intent.action}")
                }
            )
            val result =
                when (intent.action) {
                    Intents.ALARM_ALERT_ACTION,
                    Intents.ALARM_PREALARM_ACTION,
                    Intents.ACTION_MUTE,
                    Intents.ACTION_DEMUTE -> START_STICKY
                    Intents.ALARM_SNOOZE_ACTION,
                    Intents.ALARM_DISMISS_ACTION,
                    Intents.ACTION_SOUND_EXPIRED -> START_NOT_STICKY
                    else -> throw RuntimeException("Unknown action ${intent.action}")
                }

            wakeLockManager.releaseTransitionWakeLock(intent)
            result
        }
    }
}
