package com.example.rickandmorty.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.adapters.MainLoadStateAdapter
import com.example.rickandmorty.adapters.PagingAdapter
import com.example.rickandmorty.adapters.TryAgain
import com.example.rickandmorty.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater,container,false)
        binding.rcView.layoutManager = GridLayoutManager(context,2)
        val adapter = PagingAdapter()
        val tryAgain:TryAgain = {adapter.retry()}
        val adapterPlus = MainLoadStateAdapter(tryAgain)

        adapter.addLoadStateListener {
            binding.xdd.isVisible = adapter.itemCount < 1 //show retry button if cold start is failed
        }

        binding.rcView.adapter = adapter.withLoadStateFooter(adapterPlus)


        lifecycleScope.launch {
            viewModel.sflof.collectLatest{adapter.submitData(it)}
        }
        binding.xdd.setOnClickListener {
            tryAgain()
        }
        return binding.root
    }









    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}