package uz.gita.broadcastreceivers.data.local

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences(context: Context) {
    private var pref:SharedPreferences


    init {
        pref = context.getSharedPreferences("Events",Context.MODE_PRIVATE)
    }


    companion object {
        private lateinit var instance:MySharedPreferences

        fun init(context: Context){
            if (!::instance.isInitialized){
                instance = MySharedPreferences(context)
            }
        }

        fun getInstance():MySharedPreferences = instance
    }

    fun saveStateByName(name: String,state:Boolean){
        pref.edit().putBoolean(name,state).apply()
    }

    fun getStateByName(name:String):Boolean{
        return pref.getBoolean(name,true)
    }


}