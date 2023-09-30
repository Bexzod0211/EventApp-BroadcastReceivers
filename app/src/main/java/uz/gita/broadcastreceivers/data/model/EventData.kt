package uz.gita.broadcastreceivers.data.model

import androidx.annotation.DrawableRes
import uz.gita.broadcastreceivers.R

data class EventData(
    val id:Int,
    val name:String,
    val isChecked:Boolean,
    val eventName:String,
    @DrawableRes val imageRes:Int = R.drawable.ic_plane
)
