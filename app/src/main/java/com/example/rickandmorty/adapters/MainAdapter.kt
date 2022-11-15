package com.example.rickandmorty.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmorty.data.model.Entity
import com.example.rickandmorty.databinding.ItemMainBinding

class MainAdapter: RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    class MainViewHolder(val binding:ItemMainBinding):RecyclerView.ViewHolder(binding.root)
    var list = listOf<Entity>()

    fun setData(list: List<Entity>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMainBinding.inflate(inflater,parent,false)
        return MainViewHolder((binding))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.binding.avatarka.load(list[position].image)
        holder.binding.name.text = list[position].name
    }

    override fun getItemCount(): Int = list.size
}