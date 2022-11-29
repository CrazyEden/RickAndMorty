package com.example.rickandmorty.ui.characters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.EntityHeaderBinding
import com.example.rickandmorty.databinding.ItemEntityBinding


class EntityPagingAdapter: PagingDataAdapter<CharacterUiModel, EntityPagingAdapter.VHolder>(EntityDiffCallback()) {
    abstract class VHolder(view:View):RecyclerView.ViewHolder(view)

    class EntityViewHolder(private val entityBinding: ItemEntityBinding): VHolder(entityBinding.root) {
        fun bind(item: CharacterUiModel?) {
            val b = (item as CharacterUiModel.Item).entity
            entityBinding.avatarka.transitionName = b.id.toString()
            entityBinding.avatarka.load(b.image)
            entityBinding.itemMain.setOnClickListener {
                val des = AllCharactersFragmentDirections.actionAllCharactersFragmentToCharacterInfoFragment(b)
                val ext = FragmentNavigatorExtras(entityBinding.avatarka to item.entity.id.toString())
                entityBinding.avatarka.findNavController().navigate(des,ext)
            }
            entityBinding.name.text = b.name
        }
    }

    class HeaderViewHolder(private val headerBinding: EntityHeaderBinding): VHolder(headerBinding.root) {
        fun bind( item: CharacterUiModel?) {
            val str:String = (item as CharacterUiModel.Header).char
            headerBinding.titleHeader.text = str
        }
    }

    override fun getItemViewType(position: Int): Int {
        return try {
            when(getItem(position)){
                is CharacterUiModel.Item -> R.layout.item_entity
                is CharacterUiModel.Header -> R.layout.entity_header
                else -> throw NullPointerException("osibka")
            }
        }catch (e:Exception){
            super.getItemViewType(position)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            R.layout.item_entity->{
                val binding = ItemEntityBinding.inflate(inflater, parent, false)
                EntityViewHolder(binding)
            }
            R.layout.entity_header->{
                val binding = EntityHeaderBinding.inflate(inflater, parent, false)
                HeaderViewHolder(binding)
            }
            else -> throw java.lang.IllegalArgumentException("xdd")
        }

    }


    override fun onBindViewHolder(holder: VHolder, position: Int) {
        val item = getItem(position)
        when(holder){
            is EntityViewHolder -> holder.bind(item)
            is HeaderViewHolder -> holder.bind(item)
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
        if (oldItem is CharacterUiModel.Item && newItem is CharacterUiModel.Item){
            return oldItem == newItem
        }
        if (oldItem is CharacterUiModel.Header && newItem is CharacterUiModel.Header){
            return oldItem == newItem
        }
        return false
    }


}