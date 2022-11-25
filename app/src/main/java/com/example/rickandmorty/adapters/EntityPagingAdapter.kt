package com.example.rickandmorty.adapters


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmorty.CharacterUiModel
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.EntityHeaderBinding
import com.example.rickandmorty.databinding.ItemEntityBinding
import com.example.rickandmorty.fragments.characters.CharacterInfoFragment

typealias OpenInfoFragmentAction = (bundle:Bundle) ->Unit




class EntityPagingAdapter(private val openInfoFragment: OpenInfoFragmentAction): PagingDataAdapter<CharacterUiModel,EntityPagingAdapter.VHolder>(EntityDiffCallback()) {
    abstract class VHolder(view:View):RecyclerView.ViewHolder(view){
        abstract fun bind(openInfoFragment: OpenInfoFragmentAction?, item: CharacterUiModel?)
    }
    class EntityViewHolder(private val entityBinding: ItemEntityBinding):VHolder(entityBinding.root) {
        override fun bind(openInfoFragment: OpenInfoFragmentAction?, item: CharacterUiModel?) {
            val b = (item as CharacterUiModel.Item).entity
            entityBinding.itemMain.setOnClickListener {
                openInfoFragment!!(bundleOf(CharacterInfoFragment.ENTITY_KEY to b))
            }
            entityBinding.avatarka.load(b.image)
            entityBinding.name.text = b.name
        }
    }

    class HeaderViewHolder(private val headerBinding: EntityHeaderBinding):VHolder(headerBinding.root) {
        override fun bind(openInfoFragment: OpenInfoFragmentAction?, item: CharacterUiModel?) {
            val str:String = (item as CharacterUiModel.Header).char
            headerBinding.titleHeader.text = str
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is CharacterUiModel.Item -> R.layout.item_entity
            is CharacterUiModel.Header -> R.layout.entity_header
            else -> throw NullPointerException("osibka")
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == R.layout.item_entity) {
            val binding = ItemEntityBinding.inflate(inflater, parent, false)
            EntityViewHolder(binding)
        }else {
            val binding = EntityHeaderBinding.inflate(inflater, parent, false)
            HeaderViewHolder(binding)
        }
    }


    override fun onBindViewHolder(holder: VHolder, position: Int) {
        val item = getItem(position)
        when(holder){
            is EntityViewHolder -> holder.bind(openInfoFragment, item)
            is HeaderViewHolder -> holder.bind(null, item)
        }
    }
}

class EntityDiffCallback :DiffUtil.ItemCallback<CharacterUiModel>(){
    override fun areItemsTheSame(oldItem: CharacterUiModel, newItem: CharacterUiModel): Boolean {
        if (oldItem is CharacterUiModel.Item && newItem is CharacterUiModel.Item){
            return oldItem.entity == newItem.entity
        }
        if (oldItem is CharacterUiModel.Header && newItem is CharacterUiModel.Header){
            return oldItem.char == newItem.char
        }
        return false
    }

    override fun areContentsTheSame(oldItem: CharacterUiModel, newItem: CharacterUiModel): Boolean {
        return when(oldItem){
            is CharacterUiModel.Item->{
                require(newItem is CharacterUiModel.Item)
                oldItem == newItem
            }
            is CharacterUiModel.Header->{
                require(newItem is CharacterUiModel.Header)
                oldItem == newItem
            }
        }
    }


}