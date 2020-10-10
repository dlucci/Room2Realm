package com.dlucci.room2realm

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dlucci.room2realm.databinding.RowBinding
import java.util.*

class MyAdapter(days : List<String>) : RecyclerView.Adapter<MyViewHolder>() {

    private val TAG = "MyAdapter"

    val days = days



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding : RowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.row,  parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = days.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.days.text = days[position]
    }

}


class MyViewHolder(binding : RowBinding) : RecyclerView.ViewHolder(binding.root) {

    val binding = binding

}
