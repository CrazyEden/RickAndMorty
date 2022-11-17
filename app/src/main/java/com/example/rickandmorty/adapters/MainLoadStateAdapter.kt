package com.example.rickandmorty.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.ItemErrorBinding
import com.example.rickandmorty.databinding.ItemProgressingBinding

class MainLoadStateAdapter:LoadStateAdapter<MainLoadStateAdapter.ItemViewHolder>(){
    abstract class ItemViewHolder(view: View):RecyclerView.ViewHolder(view){
        abstract fun bind(loadState: LoadState)
    }
    class ErrorViewHolder(private val binding:ItemErrorBinding):ItemViewHolder(binding.root) {
        override fun bind(loadState: LoadState) {
            require(loadState is LoadState.Error)
            binding.errorText.text = loadState.error.localizedMessage
        }

    }
    class ProgressingViewHolder(private val binding: ItemProgressingBinding):ItemViewHolder(binding.root) {
        override fun bind(loadState: LoadState) {
            //empty
        }
    }


    override fun onBindViewHolder(holder: ItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemViewHolder {
        return when(loadState) {
            LoadState.Loading ->ProgressingViewHolder(
                ItemProgressingBinding.inflate(LayoutInflater.from(parent.context),parent,false))

            is LoadState.Error -> ErrorViewHolder(
                ItemErrorBinding.inflate(LayoutInflater.from(parent.context),parent,false))

            is LoadState.NotLoading -> error("no support")
        }
    }

}