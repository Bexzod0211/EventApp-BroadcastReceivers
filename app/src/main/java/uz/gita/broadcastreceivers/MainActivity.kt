package uz.gita.broadcastreceivers

import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import uz.gita.broadcastreceivers.data.local.MySharedPreferences
import uz.gita.broadcastreceivers.databinding.ActivityMainBinding
import uz.gita.broadcastreceivers.domain.repository.AppRepository
import uz.gita.broadcastreceivers.receivers.MyBroadCastReceiver
import uz.gita.broadcastreceivers.service.EventService
import uz.gita.broadcastreceivers.utils.myLog

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val repository = AppRepository()
    private val pref = MySharedPreferences.getInstance()
    private val adapter by lazy { EventAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        adapter.submitList(repository.getEvents())

        binding.apply {
            recyclerMain.adapter = adapter
            recyclerMain.layoutManager = LinearLayoutManager(this@MainActivity)
        }


        adapter.setOnItemClickListener { isChecked, eventData ->
            pref.saveStateByName(eventData.eventName,isChecked)
            startService(eventData.eventName,isChecked)
        }

        binding.btnMore.setOnClickListener {
            showPopupMenu()
        }


    }

    private fun showPopupMenu(){
        val popUpMenu = PopupMenu(this,binding.btnMore)
        popUpMenu.menuInflater.inflate(R.menu.pop_up_menu,popUpMenu.menu)
        popUpMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_share->{
                    shareApp()
                }
                R.id.menu_rate->{
                    rateApp()
                }
                R.id.menu_disable->{
                    startService("Disable",false)
                    finish()
                }
            }
            true
        }
        popUpMenu.show()
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this,EventService::class.java)
        intent.putExtra(Constants.ACTION,"Start")
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            startForegroundService(intent)
        }else {
            startService(intent)
        }
    }

    private fun rateApp(){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"))
        startActivity(intent)
    }

    private fun shareApp(){
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name)
        val shareMessage = "Download Note app to save your notes\nhttps://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

    private fun startService(action:String,isChecked:Boolean){
        val intent = Intent(this,EventService::class.java)
        intent.putExtra(Constants.ACTION,action)
        intent.putExtra(Constants.IS_CHECKED,isChecked)
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            startForegroundService(intent)
        }else {
            startService(intent)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
//        unregisterReceiver(receiverPowerConnect)
//        unregisterReceiver(receiverPowerDisconnect)
//        unregisterReceiver(receiverScreenOff)
//        unregisterReceiver(receiverScreenOn)
    }
}