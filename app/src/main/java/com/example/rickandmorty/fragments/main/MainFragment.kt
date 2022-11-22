package com.example.rickandmorty.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.example.rickandmorty.R
import com.example.rickandmorty.adapters.EntityPagingAdapter
import com.example.rickandmorty.adapters.MainLoadStateAdapter
import com.example.rickandmorty.databinding.FragmentMainBinding
import com.example.rickandmorty.fragments.info.InfoFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter:EntityPagingAdapter
    private lateinit var loadStateFooter:MainLoadStateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater,container,false)

        binding.rcView.adapter = initAdapter()
        binding.rcView.layoutManager = initLayoutManager()
        lifecycleScope.launch {
            viewModel.load().collectLatest{adapter.submitData(it)}
            //TODO shared pref get last filter state
        }

//        binding.LOADFILTER.setOnClickListener {
//            lifecycleScope.launch {
//                viewModel.load(
//                    name = "Rick"
//                ).collectLatest { adapter.submitData(it) }
//            }
//        }
        binding.mainErrorButton.setOnClickListener { adapter.retry() }
        return binding.root
    }

    private fun initAdapter(): ConcatAdapter {
        adapter = EntityPagingAdapter{
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container_view_tag, InfoFragment.newInstance(it))
                .commit()
        }
        loadStateFooter = MainLoadStateAdapter{adapter.retry()}
        adapter.addLoadStateListener {
            binding.mainErrorButton.isVisible = adapter.itemCount < 1
        }//show retry button if cold start is failed
        return adapter.withLoadStateFooter(loadStateFooter)

    }
    private fun initLayoutManager(): GridLayoutManager {
        val layoutManager = GridLayoutManager(context,2)
        layoutManager.spanSizeLookup =  object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == adapter.itemCount  && loadStateFooter.itemCount > 0) 2
                else 1
            }
        }//loadStateFooter span double size https://issuetracker.google.com/u/0/issues/178460672
        return layoutManager
    }







    

}