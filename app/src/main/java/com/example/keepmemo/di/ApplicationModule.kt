package com.example.keepmemo.di

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import android.media.AudioManager
import android.os.PowerManager
import android.telephony.TelephonyManager
import com.example.keepmemo.alarm.AlarmSetterImpl
import com.example.keepmemo.alarm.AlarmSetterInterface
import com.example.keepmemo.alarm.ISetAlarmStrategy
import com.example.keepmemo.alarm.KitKatSetter
import com.example.keepmemo.alarm.MarshmallowSetter
import com.example.keepmemo.alarm.OreoSetter
import com.example.keepmemo.alarm.util.OreoVibratorUtil
import com.example.keepmemo.alarm.util.PreOreoVibratorUtil
import com.example.keepmemo.alarm.util.SVibratorUtil
import com.example.keepmemo.alarm.util.VibratorUtilInterface
import com.example.keepmemo.alarm.util.WakeLockManager
import com.example.keepmemo.alarm.util.Wakelocks
import com.example.keepmemo.util.DeviceUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideAlarmManager(@ApplicationContext context: Context): AlarmManager {
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    @Singleton
    @Provides
    fun providePowerManager(@ApplicationContext context: Context): PowerManager {
        return context.getSystemService(Context.POWER_SERVICE) as PowerManager
    }

    @Singleton
    @Provides
    fun provideTelephonyManager(@ApplicationContext context: Context): TelephonyManager {
        return context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    }

    @Singleton
    @Provides
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Singleton
    @Provides
    fun provideAudioManager(@ApplicationContext context: Context): AudioManager {
        return context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    @Singleton
    @Provides
    fun provideWakeLocks(powerManager: PowerManager): Wakelocks {
        return WakeLockManager(powerManager)
    }

    @Singleton
    @Provides
    fun provideSetAlarmStrategy(
        @ApplicationContext context: Context,
        alarmManager: AlarmManager
    ): ISetAlarmStrategy {
        return when {
            DeviceUtil.isOreoOver() -> OreoSetter(context, alarmManager)
            DeviceUtil.isMarshmallowOver() -> MarshmallowSetter(alarmManager)
            else -> KitKatSetter(alarmManager)
        }
    }

    @Singleton
    @Provides
    fun provideAlarmSetter(
        @ApplicationContext context: Context,
        alarmManager: AlarmManager,
        setAlarmStrategy: ISetAlarmStrategy
    ): AlarmSetterInterface {
        return AlarmSetterImpl(
            context = context,
            alarmManager = alarmManager,
            setAlarmStrategy = setAlarmStrategy
        )
    }

    @Singleton
    @Provides
    fun provideVibratorUtil(
        @ApplicationContext context: Context
    ): VibratorUtilInterface {
        return when {
            DeviceUtil.isSOrOver() -> SVibratorUtil(context)
            DeviceUtil.isOreoOver() -> OreoVibratorUtil(context)
            else -> PreOreoVibratorUtil(context)
        }
    }
}
