package com.example.rickandmorty.ui.episodes


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.EpisodeHeaderBinding
import com.example.rickandmorty.databinding.ItemEpisodeBinding

typealias OpenInfoFragmentAction = (bundle:Bundle) ->Unit


class EpisodePagingAdapter: PagingDataAdapter<EpisodeUiModel,EpisodePagingAdapter.VHolder>(EntityDiffCallback()) {
    abstract class VHolder(view:View):RecyclerView.ViewHolder(view)

    class EpisodeViewHolder(private val episodeBinding: ItemEpisodeBinding):VHolder(episodeBinding.root) {
        fun bind(item: EpisodeUiModel?) {
            val b = (item as EpisodeUiModel.Item).episode
            episodeBinding.item.setOnClickListener {
                val dir= AllEpisodesFragmentDirections.actionAllEpisodesFragmentToEpisodeInfoFragment(-1,b)
                it.findNavController().navigate(dir)
            }
            episodeBinding.codeOfEpisode.text = b.codeOfEpisode
            episodeBinding.nameOfEpisode.text = b.name
            episodeBinding.airDate.text = b.airDate
        }
    }

    class HeaderViewHolder(private val headerBinding: EpisodeHeaderBinding):VHolder(headerBinding.root) {
        fun bind( item: EpisodeUiModel?) {
            val str:String = (item as EpisodeUiModel.Header).season
            headerBinding.textView.text = str
        }
    }

    override fun getItemViewType(position: Int): Int {
        return try {
            when(getItem(position)){
                is EpisodeUiModel.Item -> R.layout.item_episode
                is EpisodeUiModel.Header -> R.layout.episode_header
                else -> throw NullPointerException("osibka")
            }
        }catch (e:Exception){
            super.getItemViewType(position)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            R.layout.item_episode->{
                val binding = ItemEpisodeBinding.inflate(inflater, parent, false)
                EpisodeViewHolder(binding)
            }
            R.layout.episode_header->{
                val binding = EpisodeHeaderBinding.inflate(inflater, parent, false)
                HeaderViewHolder(binding)
            }
            else -> throw java.lang.IllegalArgumentException("xdd")
        }

    }


    override fun onBindViewHolder(holder: VHolder, position: Int) {
        val item = getItem(position)
        when(holder){
            is EpisodeViewHolder -> holder.bind(item)
            is HeaderViewHolder -> holder.bind(item)
        }
    }
}

class EntityDiffCallback :DiffUtil.ItemCallback<EpisodeUiModel>(){
    override fun areItemsTheSame(oldItem: EpisodeUiModel, newItem: EpisodeUiModel): Boolean {
        if (oldItem is EpisodeUiModel.Item && newItem is EpisodeUiModel.Item){
            return oldItem.episode.id == newItem.episode.id
        }
        if (oldItem is EpisodeUiModel.Header && newItem is EpisodeUiModel.Header){
            return oldItem.season == newItem.season
        }
        return false
    }

    override fun areContentsTheSame(oldItem: EpisodeUiModel, newItem: EpisodeUiModel): Boolean {
        if (oldItem is EpisodeUiModel.Item && newItem is EpisodeUiModel.Item){
            return oldItem == newItem
        }
        if (oldItem is EpisodeUiModel.Header && newItem is EpisodeUiModel.Header){
            return oldItem == newItem
        }
        return false
    }


}