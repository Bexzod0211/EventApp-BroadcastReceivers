package uz.gita.broadcastreceivers.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import uz.gita.broadcastreceivers.Constants
import uz.gita.broadcastreceivers.MainActivity
import uz.gita.broadcastreceivers.R
import uz.gita.broadcastreceivers.data.local.MySharedPreferences
import uz.gita.broadcastreceivers.receivers.MyBroadCastReceiver

class EventService : Service() {
    private var receiverPowerConnected = MyBroadCastReceiver()
    private var receiverPowerDisconnected = MyBroadCastReceiver()
    private var receiverScreenOn = MyBroadCastReceiver()
    private var receiverScreenOff = MyBroadCastReceiver()
    private var receiverAirplaneMode = MyBroadCastReceiver()
    private var receiverBatteryLow = MyBroadCastReceiver()
    private var receiverBatteryOk = MyBroadCastReceiver()
    private var receiverTimeChanged = MyBroadCastReceiver()
    private var receiverShutDown = MyBroadCastReceiver()
    private val pref = MySharedPreferences.getInstance()

    private val CHANNEL_ID = "Events"

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createChannel()
        registerAllEvents()

    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.getStringExtra(Constants.ACTION)
        val isChecked = intent?.getBooleanExtra(Constants.IS_CHECKED, true)
        doCommand(action ?: "", isChecked ?: true)
        return START_NOT_STICKY
    }

    private fun doCommand(action: String, isChecked: Boolean) {
        when (action) {
            Intent.ACTION_SCREEN_ON -> {
                if (isChecked) {
                    registerReceiver(receiverScreenOn, IntentFilter(Intent.ACTION_SCREEN_ON))
                } else unregisterReceiver(receiverScreenOn)
            }

            Intent.ACTION_SCREEN_OFF -> {
                if (isChecked) {
                    registerReceiver(receiverScreenOff, IntentFilter(Intent.ACTION_SCREEN_OFF))
                } else unregisterReceiver(receiverScreenOff)
            }

            Intent.ACTION_POWER_CONNECTED -> {
                if (isChecked)
                    registerReceiver(receiverPowerConnected, IntentFilter(Intent.ACTION_POWER_CONNECTED))
                else
                    unregisterReceiver(receiverPowerConnected)
            }

            Intent.ACTION_POWER_DISCONNECTED -> {
                if (isChecked) {
                    registerReceiver(receiverPowerDisconnected, IntentFilter(Intent.ACTION_POWER_DISCONNECTED))
                } else {
                    unregisterReceiver(receiverPowerDisconnected)
                }
            }

            Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                if (isChecked)
                    registerReceiver(receiverAirplaneMode, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
                else
                    unregisterReceiver(receiverAirplaneMode)
            }

            Intent.ACTION_BATTERY_LOW -> {
                if (isChecked)
                    registerReceiver(receiverBatteryLow, IntentFilter(Intent.ACTION_BATTERY_LOW))
                else
                    unregisterReceiver(receiverBatteryLow)
            }

            Intent.ACTION_BATTERY_OKAY -> {
                if (isChecked)
                    registerReceiver(receiverBatteryOk, IntentFilter(Intent.ACTION_BATTERY_OKAY))
                else
                    unregisterReceiver(receiverBatteryOk)
            }

            Intent.ACTION_TIME_CHANGED -> {
                if (isChecked)
                    registerReceiver(receiverTimeChanged, IntentFilter(Intent.ACTION_TIME_CHANGED))
                else
                    unregisterReceiver(receiverTimeChanged)
            }

            Intent.ACTION_SHUTDOWN -> {
                if (isChecked)
                    registerReceiver(receiverShutDown, IntentFilter(Intent.ACTION_TIME_CHANGED))
                else
                    unregisterReceiver(receiverShutDown)
            }

            "Disable" -> {
                unregisterReceivers()
                ServiceCompat.stopForeground(this, ServiceCompat.STOP_FOREGROUND_REMOVE)
            }
            "Start"->{
                createNotification()
            }


        }
    }

    private fun registerAllEvents() {
        if (pref.getStateByName(Intent.ACTION_POWER_CONNECTED))
            registerReceiver(receiverPowerConnected, IntentFilter(Intent.ACTION_POWER_CONNECTED))
        if (pref.getStateByName(Intent.ACTION_POWER_DISCONNECTED))
            registerReceiver(receiverPowerDisconnected, IntentFilter(Intent.ACTION_POWER_DISCONNECTED))
        if (pref.getStateByName(Intent.ACTION_SCREEN_ON))
            registerReceiver(receiverScreenOn, IntentFilter(Intent.ACTION_SCREEN_ON))
        if (pref.getStateByName(Intent.ACTION_SCREEN_OFF))
            registerReceiver(receiverScreenOff, IntentFilter(Intent.ACTION_SCREEN_OFF))
        if (pref.getStateByName(Intent.ACTION_AIRPLANE_MODE_CHANGED))
            registerReceiver(receiverAirplaneMode, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
        if (pref.getStateByName(Intent.ACTION_BATTERY_LOW))
            registerReceiver(receiverBatteryLow, IntentFilter(Intent.ACTION_BATTERY_LOW))
        if (pref.getStateByName(Intent.ACTION_BATTERY_OKAY))
            registerReceiver(receiverBatteryOk, IntentFilter(Intent.ACTION_BATTERY_OKAY))
        if (pref.getStateByName(Intent.ACTION_TIME_CHANGED))
            registerReceiver(receiverTimeChanged, IntentFilter(Intent.ACTION_TIME_CHANGED))
        if (pref.getStateByName(Intent.ACTION_SHUTDOWN))
            registerReceiver(receiverShutDown, IntentFilter(Intent.ACTION_SHUTDOWN))
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(CHANNEL_ID, "Events aoo", importance)

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceivers()
    }

    private fun unregisterReceivers() {
        if (pref.getStateByName(Intent.ACTION_POWER_CONNECTED))
            unregisterReceiver(receiverPowerConnected)
        if (pref.getStateByName(Intent.ACTION_POWER_DISCONNECTED))
            unregisterReceiver(receiverPowerDisconnected)
        if (pref.getStateByName(Intent.ACTION_SCREEN_ON))
            unregisterReceiver(receiverScreenOn)
        if (pref.getStateByName(Intent.ACTION_SCREEN_OFF))
            unregisterReceiver(receiverScreenOff)
        if (pref.getStateByName(Intent.ACTION_AIRPLANE_MODE_CHANGED))
            unregisterReceiver(receiverAirplaneMode)
        if (pref.getStateByName(Intent.ACTION_BATTERY_LOW))
            unregisterReceiver(receiverBatteryLow)
        if (pref.getStateByName(Intent.ACTION_BATTERY_OKAY))
            unregisterReceiver(receiverBatteryOk)
        if (pref.getStateByName(Intent.ACTION_TIME_CHANGED))
            unregisterReceiver(receiverTimeChanged)
        if (pref.getStateByName(Intent.ACTION_SHUTDOWN))
            unregisterReceiver(receiverShutDown)
    }


    private fun createNotification() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_bell)
            .setContentIntent(pendingIntent)
            .setContentTitle("Event detector enabled")
            .setOngoing(true)


        startForeground(1, notificationBuilder.build())


    }

}