package com.example.keepmemo.util

import android.os.Build

object DeviceUtil {

    fun isSorOver(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
}
