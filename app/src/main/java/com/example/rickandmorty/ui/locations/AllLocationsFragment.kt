package com.example.rickandmorty.ui.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.databinding.FragmentAllLocationsBinding
import com.example.rickandmorty.ui.characters.MainLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllLocationsFragment : Fragment() {
    private lateinit var binding:FragmentAllLocationsBinding
    private lateinit var adapter:LocationsPagingAdapter
    private lateinit var loadStateFooter:MainLoadStateAdapter
    private val vModel:AllLocationViewModel by viewModels()
    private var bool = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (::binding.isInitialized) return binding.root
        if (bool) return binding.root
        binding = FragmentAllLocationsBinding.inflate(inflater,container,false)
        binding.rcLocation.adapter = initAdapter()
        binding.rcLocation.layoutManager = LinearLayoutManager(context)
        bool = true
        lifecycleScope.launch{
            vModel.getFlow().collectLatest { adapter.submitData(it) }
        }




        return binding.root
    }

    private fun initAdapter(): ConcatAdapter {
        adapter = LocationsPagingAdapter()
        loadStateFooter = MainLoadStateAdapter{adapter.retry()}
        adapter.addLoadStateListener {
            binding.mainErrorButton.isVisible = adapter.itemCount < 1
        }//show retry button if cold start is failed
        return adapter.withLoadStateFooter(loadStateFooter)
    }

}