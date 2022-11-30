package com.example.rickandmorty.ui.locations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.data.model.Location
import com.example.rickandmorty.databinding.ItemLocationBinding

class LocationsPagingAdapter :PagingDataAdapter<Location,LocationsPagingAdapter.LocationViewHolder>(LocationDiffCallback()){
    class LocationViewHolder(val binding: ItemLocationBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.name.text = item?.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLocationBinding.inflate(inflater,parent,false)
        return LocationViewHolder(binding)
    }


}

class LocationDiffCallback : DiffUtil.ItemCallback<Location>() {
    override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem.id == newItem.id
    }

}
