package com.example.rickandmorty.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.ItemErrorBinding

typealias TryAgain = () ->Unit

class MainLoadStateAdapter(private val tryAgain: TryAgain):LoadStateAdapter<MainLoadStateAdapter.Holder>(){
    class Holder(private val binding: ItemErrorBinding,
                  private val tryAgain:TryAgain):RecyclerView.ViewHolder(binding.root){
        init {
            binding.errBtn.setOnClickListener {
                tryAgain()
            }
        }
        fun bind(loadState: LoadState){
            if (loadState is LoadState.Error){
                binding.errBtn.isVisible = true
                binding.prog.isVisible = false
            }else {
                binding.prog.isVisible = true
            }
        }
    }



    override fun onBindViewHolder(holder: Holder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemErrorBinding.inflate(inflater,parent,false)
        return Holder(binding, tryAgain)
    }

}