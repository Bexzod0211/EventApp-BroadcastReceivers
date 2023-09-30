package uz.gita.broadcastreceivers.domain.repository

import android.content.Intent
import uz.gita.broadcastreceivers.Constants
import uz.gita.broadcastreceivers.R
import uz.gita.broadcastreceivers.data.local.MySharedPreferences
import uz.gita.broadcastreceivers.data.model.EventData

class AppRepository {

    private val pref: MySharedPreferences = MySharedPreferences.getInstance()


    fun getEvents(): ArrayList<EventData> {
        val events = arrayListOf(
            EventData(id = 1, name = "Screen on", isChecked = pref.getStateByName(Constants.SCREEN_ON_MODE), eventName = Intent.ACTION_SCREEN_ON, imageRes = R.drawable.ic_screen_on),
            EventData(2, "Screen off", pref.getStateByName(Constants.SCREEN_OFF_MODE), Intent.ACTION_SCREEN_OFF,R.drawable.ic_screen_off),
            EventData(3, "Airplane mode changed", pref.getStateByName(Constants.AIRPLANE_MODE), Intent.ACTION_AIRPLANE_MODE_CHANGED,R.drawable.ic_plane),
            EventData(4, "Power connected", pref.getStateByName(Constants.POWER_CONNECTED), Intent.ACTION_POWER_CONNECTED,R.drawable.power_connected),
            EventData(5, "Power disconnected", pref.getStateByName(Constants.POWER_DISCONNECTED), Intent.ACTION_POWER_DISCONNECTED,R.drawable.ic_power_disconnected),
            EventData(6, "Battery low", pref.getStateByName(Constants.BATTERY_LOW), Intent.ACTION_BATTERY_LOW,R.drawable.ic_battery_low),
            EventData(7, "Battery ok", pref.getStateByName(Constants.BATTERY_FULL), Intent.ACTION_BATTERY_OKAY,R.drawable.ic_battery_full),
            EventData(8, "Time changed", pref.getStateByName(Constants.TIME_CHANGED), Intent.ACTION_TIME_CHANGED,R.drawable.ic_time),
            EventData(9, "Shut down", pref.getStateByName(Constants.SHUT_DOWN), Intent.ACTION_SHUTDOWN,R.drawable.ic_shut_down),

        )

        return events
    }

}