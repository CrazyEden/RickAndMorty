package com.example.rickandmorty.adapters


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.rickandmorty.data.model.Entity
import com.example.rickandmorty.databinding.ItemMainBinding
import com.example.rickandmorty.fragments.info.InfoFragment

typealias EntityFragment = (bundle:Bundle) ->Unit

class PagingAdapter(private val entityFragment: EntityFragment): PagingDataAdapter<Entity,PagingAdapter.MainViewHolder>(EntityDiffCallback()) {
    class MainViewHolder(val binding:ItemMainBinding):RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMainBinding.inflate(inflater,parent,false)
        return MainViewHolder((binding))
    }


    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = getItem(position)

        holder.binding.itemMain.setOnClickListener {
            entityFragment(bundleOf(
                InfoFragment.IMAGEVIEW_KEY  to item?.image,
                InfoFragment.NAME_KEY       to item?.name,
                InfoFragment.STATUS_KEY     to item?.status,
                InfoFragment.SPECIES_KEY    to item?.species,
                InfoFragment.TYPE_KEY       to item?.type,
                InfoFragment.GENDER_KEY     to item?.gender,
                InfoFragment.NAMEORI_KEY    to item?.origin?.name,
                InfoFragment.NAMELOC_KEY    to item?.location?.name,
                InfoFragment.CREATED_KEY    to item?.created)
            )
        }
        try {
            holder.binding.avatarka.load(item?.image){
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }catch (e :Exception){
            println("kraaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaashhhhhhhh")
        }
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