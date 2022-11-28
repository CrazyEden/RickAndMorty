package com.example.rickandmorty.ui.episodes

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
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentAllEpisodesBinding
import com.example.rickandmorty.ui.characters.MainLoadStateAdapter
import com.example.rickandmorty.ui.episode.EpisodeInfoFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllEpisodesFragment : Fragment() {
    lateinit var binding:FragmentAllEpisodesBinding
    private val viewModel: AllEpisodesViewModel by viewModels()
    private lateinit var adapter:EpisodePagingAdapter
    private lateinit var loadStateFooter:MainLoadStateAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllEpisodesBinding.inflate(inflater,container,false)

        binding.rcView.adapter = initAdapter()
        binding.rcView.layoutManager = LinearLayoutManager(context)
        initClickListeners()
        search()
        initChangeListeners()

        return binding.root
    }

    private fun search(){
        lifecycleScope.launch {
            viewModel.load().collectLatest { adapter.submitData(it) }
        }
    }

    private fun initAdapter(): ConcatAdapter {
        adapter = EpisodePagingAdapter{
            parentFragmentManager.beginTransaction()
                .hide(this)
                .addToBackStack(null)
                .add(R.id.fragment_container_view_tag, EpisodeInfoFragment.newInstanceByEpisode(it))
                .commit()
        }
        loadStateFooter = MainLoadStateAdapter{adapter.retry()}
        adapter.addLoadStateListener {
            binding.mainErrorButton.isVisible = adapter.itemCount < 1
        }//show retry button if cold start is failed
        return adapter.withLoadStateFooter(loadStateFooter)

    }



    private fun initClickListeners(){
        binding.mainErrorButton.setOnClickListener { adapter.retry() }


    }

    private fun initChangeListeners() {

    }
}