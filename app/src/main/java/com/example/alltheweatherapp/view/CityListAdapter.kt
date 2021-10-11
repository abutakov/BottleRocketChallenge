package com.example.alltheweatherapp.view

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alltheweatherapp.databinding.CityListLayoutBinding
import com.example.alltheweatherapp.model.City

class CityListAdapter(private val dataset: List<City>,
    private val callback: (Int) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class CityListViewHolder(private val binding: CityListLayoutBinding):
        RecyclerView.ViewHolder(binding.root){

            fun onBind(dataItem: City, callback: (Int)-> Unit){
                binding.root.setOnClickListener {
                    callback.invoke(dataItem.geonameid)
                }
                binding.cityName.text = dataItem.name
                binding.cityGeoId.text = dataItem.geonameid.toString()
                binding.cityTimeZone.text = dataItem.timezone
            }
    }

    class CityListEmptyViewHolder(private val view: View):
        RecyclerView.ViewHolder(view){
            fun onBind(){
                view.findViewById<TextView>(android.R.id.text1).text = "No city has founded."
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            1-> CityListViewHolder(CityListLayoutBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
            else->
                CityListEmptyViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.simple_list_item_1,
                    parent,
                    false
                ))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is CityListViewHolder -> holder.onBind(dataset[position], callback)
            is CityListEmptyViewHolder -> holder.onBind()
            else-> throw IllegalAccessException("Incorrect ViewHolder")
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(dataset.isNotEmpty()) 1 else 0
    }
}