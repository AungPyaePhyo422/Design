package com.example.customdesign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.customdesign.adapter.AdapterItemClickListener
import com.example.customdesign.adapter.MedicineCountAdapter
import com.example.customdesign.data.MedicineDetail
import com.example.customdesign.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity() {

    private var arr = mutableListOf<MedicineDetail>()

    private lateinit var binding: ActivityMainBinding

    private var tot : Int = 0

    var select : Boolean = false

    val adapterr = MedicineCountAdapter(
        object : AdapterItemClickListener{

            override fun addCountClickListener(item: MedicineDetail) {
                item.count = item.count + 1
                tot += item.price
                binding.tvTotalPrice.text = tot.toString() + " MMK"
            }

            override fun removeCountClickListener(item: MedicineDetail) {
                if (item.count > 0){
                    item.count = item.count - 1
                    tot -= item.price
                    binding.tvTotalPrice.text = "$tot MMK"
                }
            }

            override fun checkListener(): Boolean {
                return select
            }

            override fun totalAmount(total: MedicineDetail) {
                tot = total.count * total.price
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        arr.add(MedicineDetail("Biogestic", 1500, 0))
        arr.add(MedicineDetail("Albendazole", 2000, 0))
        arr.add(MedicineDetail("Cefditoren Pivoxil 100mg", 3500, 0))
        arr.add(MedicineDetail("Hydroxychlogroquine Sulfate", 4000, 0))
        arr.add(MedicineDetail("Tenofovir alafenamide", 8400, 0))
        arr.add(MedicineDetail("ginkgo bilba phytosome", 33000, 0))

        binding.tvUnselectAll.setOnClickListener{
            select = false
            adapterr.notifyDataSetChanged()
        }

        binding.tvSelectAll.setOnClickListener{
            select = true
            adapterr.notifyDataSetChanged()
        }

        binding.rvMedicineList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterr
        }

        binding.tvTotalPrice.text = tot.toString()

        adapterr.submitList(arr)

    }
}