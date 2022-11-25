package com.example.rickandmorty.fragments.characters

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
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.adapters.EntityPagingAdapter
import com.example.rickandmorty.adapters.MainLoadStateAdapter
import com.example.rickandmorty.databinding.FragmentAllCharactersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllCharactersFragment : Fragment(R.layout.fragment_all_characters) {

    private lateinit var binding: FragmentAllCharactersBinding
    private val viewModel: AllCharactersViewModel by viewModels()
    private lateinit var adapter:EntityPagingAdapter
    private lateinit var loadStateFooter:MainLoadStateAdapter
    private lateinit var textFilter:String
    private var genderFilter:String? = null
    private var statusFilter:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllCharactersBinding.inflate(inflater,container,false)

        binding.rcView.adapter = initAdapter()
        binding.rcView.layoutManager = initLayoutManager()
        initClickListeners()
        search(gender = viewModel.getGender(),
            name = viewModel.getText(),
            status = viewModel.getStatus())
        initChangeListeners()
        return binding.root
    }



    private fun search(name:String?,status:String?,gender:String?,mFilter:Boolean = false){

        lifecycleScope.launch {
            viewModel.load(
                name=name,
                status=status,
                gender=gender,
                mFilter = mFilter
            ).collectLatest { adapter.submitData(it) }
        }

        binding.searchPanel.searchLayout.visibility = View.GONE
        binding.LOADFILTER.show()

    }

    private fun initAdapter(): ConcatAdapter {
        adapter = EntityPagingAdapter{
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container_view_tag, CharacterInfoFragment.newInstanceByEntity(it))
                .commit()
        }
        loadStateFooter = MainLoadStateAdapter{adapter.retry()}
        adapter.addLoadStateListener {
            binding.mainErrorButton.isVisible = adapter.itemCount < 1
        }//show retry button if cold start is failed
        return adapter.withLoadStateFooter(loadStateFooter)

    }

    private fun initLayoutManager(): LinearLayoutManager {
        val layoutManager =
            GridLayoutManager(context,2)
        layoutManager.spanSizeLookup =  object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when(adapter.getItemViewType(position)){
                    R.layout.entity_header->2
                    else ->{
                        if (position == adapter.itemCount  && loadStateFooter.itemCount > 0) 2
                        else 1
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
            binding.searchPanel.searchLayout.visibility = View.GONE
        }
        binding.searchPanel.clearFilter.setOnClickListener {
            binding.searchPanel.textName.text
            binding.searchPanel.filterStatus.clearCheck()
            binding.searchPanel.filterGender.clearCheck()
        }
        binding.searchPanel.search.setOnClickListener {
            search(gender = genderFilter,
            status = statusFilter,
            name = textFilter)
        }
        binding.LOADFILTER.setOnClickListener {
            binding.LOADFILTER.hide()
            binding.searchPanel.searchLayout.visibility = View.VISIBLE
        }
    }

    private fun initChangeListeners() {
        binding.searchPanel.textName.setOnEditorActionListener { _, _, _ ->
            search(gender = genderFilter,
                status = statusFilter,
                name = textFilter)
            true
        }
        binding.searchPanel.textName.addTextChangedListener {
            textFilter = it.toString()
        }
        binding.searchPanel.filterStatus.setOnCheckedChangeListener { group, checkedId ->
            statusFilter = runCatching {
                group.findViewById<RadioButton>(checkedId).text.toString()
            }.getOrDefault("")
        }
        binding.searchPanel.filterGender.setOnCheckedChangeListener { group, checkedId ->
            genderFilter = runCatching {
                group.findViewById<RadioButton>(checkedId).text?.toString()
            }.getOrDefault("")
        }
    }

    companion object{
        const val NAME_SHARED_PREFERENCES = "sp"
        const val KEY_TEXT_FILTER = "text"
        const val KEY_GENDER_FILTER = "gender"
        const val KEY_STATUS_FILTER = "status"
    }




    

}