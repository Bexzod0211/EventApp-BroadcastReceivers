package uz.gita.broadcastreceivers.app

import android.app.Application
import uz.gita.broadcastreceivers.data.local.MySharedPreferences

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        MySharedPreferences.init(this)
    }
}