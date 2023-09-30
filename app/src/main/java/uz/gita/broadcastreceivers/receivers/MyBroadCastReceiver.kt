package uz.gita.broadcastreceivers.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import uz.gita.broadcastreceivers.R

class MyBroadCastReceiver : BroadcastReceiver() {
    private lateinit var audio:MediaPlayer


    override fun onReceive(context: Context, intent: Intent) {
        val pilotMode = intent.getBooleanExtra("state",false)
        when(intent.action){
            Intent.ACTION_POWER_CONNECTED ->{
                audio = MediaPlayer.create(context, R.raw.power_connected)
                audio.start()
            }
            Intent.ACTION_POWER_DISCONNECTED ->{
                audio = MediaPlayer.create(context,R.raw.power_disconnected)
                audio.start()
            }
            Intent.ACTION_SCREEN_ON->{
                audio = MediaPlayer.create(context,R.raw.screen_on)
                audio.start()
            }
            Intent.ACTION_SCREEN_OFF->{
                audio = MediaPlayer.create(context,R.raw.screen_off)
                audio.start()
            }
            Intent.ACTION_AIRPLANE_MODE_CHANGED->{
                if (pilotMode){
                    audio = MediaPlayer.create(context,R.raw.airplane_on)
                    audio.start()
                }else {
                    audio = MediaPlayer.create(context,R.raw.airplane_off)
                    audio.start()
                }
            }
            Intent.ACTION_BATTERY_LOW->{
                audio = MediaPlayer.create(context,R.raw.battery_low)
                audio.start()
            }
            Intent.ACTION_BATTERY_OKAY->{
                audio = MediaPlayer.create(context,R.raw.battery_ok)
                audio.start()
            }
            Intent.ACTION_TIME_CHANGED->{
                audio = MediaPlayer.create(context,R.raw.time_changed)
                audio.start()
            }
            Intent.ACTION_SHUTDOWN->{
                audio = MediaPlayer.create(context,R.raw.shut_down)
                audio.start()
            }
        }

    }
}