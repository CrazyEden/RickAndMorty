package com.example.rickandmorty.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmorty.R
import com.example.rickandmorty.data.model.Entity
import com.example.rickandmorty.databinding.ItemMainBinding


class MainAdapter: PagingDataAdapter<Entity,MainAdapter.MainViewHolder>(EntityDiffCallback()) {
    class MainViewHolder(val binding:ItemMainBinding):RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMainBinding.inflate(inflater,parent,false)
        return MainViewHolder((binding))
    }


    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = getItem(position)

        holder.binding.itemMain.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_infoFragment, bundleOf(
                "imageView" to item?.image,
                "name" to item?.name,
                "status" to item?.status,
                "species" to item?.species,
                "type" to item?.type,
                "gender" to item?.gender,
                "nameOri" to item?.origin?.name,
                "nameLoc" to item?.location?.name,
                "created" to item?.created)
            )
        }
        holder.binding.avatarka.load(item?.image)
        holder.binding.name.text = item?.name
    }
}

class EntityDiffCallback :DiffUtil.ItemCallback<Entity>(){
    override fun areItemsTheSame(oldItem: Entity, newItem: Entity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Entity, newItem: Entity): Boolean {
        return oldItem==newItem
    }


}