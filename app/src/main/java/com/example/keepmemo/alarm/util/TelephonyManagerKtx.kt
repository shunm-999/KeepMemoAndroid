package com.example.keepmemo.alarm.util

import android.os.Build
import android.telephony.PhoneStateListener
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.Executor

@Suppress("DEPRECATION")
fun TelephonyManager.observePhoneState(): Flow<Boolean> {
    return callbackFlow<Boolean> {
        val listener =
            object : PhoneStateListener() {
                @Deprecated(
                    "Deprecated in Java", ReplaceWith("TelephonyCallback.CallStateListener")
                )
                override fun onCallStateChanged(state: Int, ignored: String) {
                    trySend(state != TelephonyManager.CALL_STATE_IDLE)
                }
            }

        trySend(callState != TelephonyManager.CALL_STATE_IDLE)

        listen(listener, PhoneStateListener.LISTEN_CALL_STATE)
        awaitClose {
            listen(listener, PhoneStateListener.LISTEN_NONE)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
fun TelephonyManager.observeCallState(
    executor: Executor
): Flow<Boolean> {
    return callbackFlow<Boolean> {
        val callback = object : TelephonyCallback(), TelephonyCallback.CallStateListener {
            override fun onCallStateChanged(state: Int) {
                trySend(state != TelephonyManager.CALL_STATE_IDLE)
            }
        }
        registerTelephonyCallback(executor, callback)
        awaitClose {
            unregisterTelephonyCallback(callback)
        }
    }
}
