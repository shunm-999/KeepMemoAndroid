package com.example.keepmemo.util

import android.os.Build

object DeviceUtil {

    fun isMarshmallowOver(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    fun isOreoOver(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    fun isOreoMR1Over(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1

    fun isSOrOver(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
}
