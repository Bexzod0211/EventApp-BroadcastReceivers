package uz.gita.broadcastreceivers

import android.content.Intent

object Constants {
    val SCREEN_ON_MODE = Intent.ACTION_SCREEN_ON
    val SCREEN_OFF_MODE = Intent.ACTION_SCREEN_OFF
    val POWER_CONNECTED = Intent.ACTION_POWER_CONNECTED
    val POWER_DISCONNECTED = Intent.ACTION_POWER_DISCONNECTED
    val AIRPLANE_MODE = Intent.ACTION_AIRPLANE_MODE_CHANGED
    val BATTERY_LOW = Intent.ACTION_BATTERY_LOW
    val BATTERY_FULL = Intent.ACTION_BATTERY_OKAY
    val TIME_CHANGED = Intent.ACTION_TIME_CHANGED
    val SHUT_DOWN = Intent.ACTION_SHUTDOWN
    val ACTION = "action"
    val IS_CHECKED = "isChecked"

}