package com.example.rickandmorty.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentAllCharactersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllCharactersFragment : Fragment() {

    private lateinit var binding: FragmentAllCharactersBinding
    private val viewModel: AllCharactersViewModel by viewModels()
    private lateinit var adapter: EntityPagingAdapter
    private lateinit var loadStateFooter:MainLoadStateAdapter
    private lateinit var textFilter:String
    private lateinit var genderFilter:String
    private lateinit var statusFilter:String
    private var statusSwitcher:Boolean = false
    private var bool = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (bool) return binding.root //avoid fragment reload if it was loaded
        binding = FragmentAllCharactersBinding.inflate(inflater,container,false)
        binding.rcView.adapter = initAdapter()
        binding.rcView.layoutManager = initLayoutManager()
        initClickListeners()
        initSearchPanel()
        search()
        initChangeListeners()
        bool = true
        return binding.root
    }

    private fun initSearchPanel() {
        textFilter = viewModel.getText()
        genderFilter = viewModel.getGender()
        statusFilter = viewModel.getStatus()
        statusSwitcher = viewModel.getSwitcher()
        binding.searchPanel.ifStartWithSwitcher.isChecked = viewModel.getSwitcher()
        binding.searchPanel.textName.setText(textFilter)
        binding.searchPanel.filterStatus.check(when(statusFilter){
            "alive"->1
            "dead"->2
            "unknown"->3
            else ->-1
        })
        binding.searchPanel.filterGender.check(when (genderFilter){
            "female"-> 4
            "male"->5
            "genderless"->6
            "unknown"->7
            else -> -1

        })
    }


    private fun search(){

        lifecycleScope.launch {
            viewModel.load(
                name=textFilter,
                status=statusFilter,
                gender=genderFilter,
                mFilter = statusSwitcher
            ).collectLatest { adapter.submitData(it) }
        }

        binding.searchPanel.root.visibility = View.GONE
        binding.LOADFILTER.show()

    }

    private fun initAdapter(): ConcatAdapter {
        adapter = EntityPagingAdapter()
        loadStateFooter = MainLoadStateAdapter{adapter.retry()}
        adapter.addLoadStateListener {
            if (it.append is LoadState.NotLoading && adapter.itemCount < 1) {
                binding.mainErrorButton.isVisible = true
            }
            else binding.mainErrorButton.visibility = View.GONE
        }//show retry button if cold start is failed
        return adapter.withLoadStateFooter(loadStateFooter)

    }

    private fun initLayoutManager(): LinearLayoutManager {
        val layoutManager =
            GridLayoutManager(context,2)
        layoutManager.spanSizeLookup =  object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when(adapter.getItemViewType(position)){
                    R.layout.entity_header->2//header
                    else ->{
                        if (position == adapter.itemCount  && loadStateFooter.itemCount > 0) 2//error bottom item
                        else 1//default item
                    }
                }
            }
        }
        return layoutManager
    }

    private fun initClickListeners(){
        binding.mainErrorButton.setOnClickListener { adapter.retry() }

        binding.searchPanel.cancelSearch.setOnClickListener {
            binding.LOADFILTER.show()
            binding.searchPanel.root.visibility = View.GONE
        }
        binding.searchPanel.clearFilter.setOnClickListener {
            binding.searchPanel.textName.setText("")
            binding.searchPanel.filterStatus.clearCheck()
            binding.searchPanel.filterGender.clearCheck()
        }
        binding.searchPanel.search.setOnClickListener {
            search()
        }
        binding.LOADFILTER.setOnClickListener {
            binding.LOADFILTER.hide()
            binding.searchPanel.root.visibility = View.VISIBLE
        }

        binding.searchPanel.ifStartWithSwitcher.setOnCheckedChangeListener { _, isChecked ->
            statusSwitcher = isChecked
        }
    }

    private fun initChangeListeners() {
        binding.searchPanel.textName.setOnEditorActionListener { _, _, _ ->
            search()
            true
        }
        binding.searchPanel.textName.addTextChangedListener {
            textFilter = runCatching {
                it.toString()
            }.getOrDefault("")
        }
        binding.searchPanel.filterStatus.setOnCheckedChangeListener { group, checkedId ->
            statusFilter = runCatching {
                group.findViewById<RadioButton>(checkedId).text.toString()
            }.getOrDefault("")
        }
        binding.searchPanel.filterGender.setOnCheckedChangeListener { group, checkedId ->
            genderFilter = runCatching {
                group.findViewById<RadioButton>(checkedId).text?.toString()
            }.getOrDefault("")!!
        }
    }

    companion object{
        const val NAME_SHARED_PREFERENCES = "sp"
        const val KEY_TEXT_FILTER = "text"
        const val KEY_GENDER_FILTER = "gender"
        const val KEY_STATUS_FILTER = "status"
        const val KEY_SWITCHER_FILTER = "switcher"
    }

    override fun onPause() {
        viewModel.saveSearchState(
            text = textFilter,
            gender = genderFilter,
            status = statusFilter,
            switcherState = statusSwitcher
        )
        super.onPause()
    }


    

}