package uz.gita.broadcastreceivers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.broadcastreceivers.data.model.EventData
import uz.gita.broadcastreceivers.databinding.ItemEventBinding

class EventAdapter : ListAdapter<EventData,EventAdapter.Holder>(MyDiffUtil) {
    private lateinit var onItemClickListener:(Boolean,EventData)->Unit
    object MyDiffUtil : DiffUtil.ItemCallback<EventData>(){
        override fun areItemsTheSame(oldItem: EventData, newItem: EventData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EventData, newItem: EventData): Boolean {
            return oldItem == newItem
        }

    }

    inner class Holder(private val binding:ItemEventBinding):RecyclerView.ViewHolder(binding.root){

        init {
            binding.btnSwitch.setOnCheckedChangeListener { compoundButton, isChecked ->
                onItemClickListener.invoke(isChecked,getItem(adapterPosition))
            }
        }

        fun bind(pos:Int){
            getItem(pos).apply {
                binding.apply {
                    btnSwitch.text = name
                    btnSwitch.isChecked = isChecked
                    image.setImageResource(imageRes)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemEventBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }


    fun setOnItemClickListener(block:(Boolean,EventData)->Unit){
        onItemClickListener = block
    }
}