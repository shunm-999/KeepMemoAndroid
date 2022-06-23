package com.example.keepmemo.util

import android.os.Build

object DeviceUtil {

    fun isMarshmallowOver(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    fun isOreoOver(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    fun isSorOver(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
}
