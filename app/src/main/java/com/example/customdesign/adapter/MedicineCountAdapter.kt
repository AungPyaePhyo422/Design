package com.example.customdesign.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioGroup.OnCheckedChangeListener
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.customdesign.data.MedicineDetail
import com.example.customdesign.databinding.ItemMedicineListBinding
import kotlin.properties.Delegates

class MedicineCountAdapter(val event : AdapterItemClickListener) : ListAdapter<MedicineDetail, MedicineCountAdapter.MyViewHolder>(

    object : DiffUtil.ItemCallback<MedicineDetail>(){
        override fun areItemsTheSame(oldItem: MedicineDetail, newItem: MedicineDetail): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MedicineDetail, newItem: MedicineDetail): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }

    }

) {

     var totalAmount : Int = 0

    class MyViewHolder(val binding : ItemMedicineListBinding, val listener : AdapterItemClickListener) : RecyclerView.ViewHolder(binding.root) {

        lateinit var getCurrentItem : MedicineDetail

        init {

            binding.btnAddCount.setOnClickListener{
                getCurrentItem.apply {
                    listener.addCountClickListener(this)
                    bind(this)
                }
            }

            binding.btnRemoveCount.setOnClickListener{
                getCurrentItem.apply {
                    listener.removeCountClickListener(this)
                    bind(this)
                }
            }

        }

        fun bind(medicineDetail: MedicineDetail){

            getCurrentItem = medicineDetail

            binding.apply {
                checkBox.isChecked = listener.checkListener()
                tvMedicineName.text = getCurrentItem.name
                tvMedicinePrice.text = " ${getCurrentItem.price} MMK"
                tvMedicineCount.text = getCurrentItem.count.toString()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemMedicineListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view, event)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)

        totalAmount += currentItem.price * currentItem.count
        Log.d("Adapterchanges", totalAmount.toString())

        holder.listener.totalAmount(totalAmount)
    }

}

interface AdapterItemClickListener{

    fun addCountClickListener(item : MedicineDetail)

    fun removeCountClickListener(item : MedicineDetail)

    fun checkListener() : Boolean

    fun totalAmount(total : Int){

    }

}