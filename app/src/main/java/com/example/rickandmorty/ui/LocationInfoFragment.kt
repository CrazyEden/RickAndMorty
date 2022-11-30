package com.example.rickandmorty.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.rickandmorty.data.model.Location
import com.example.rickandmorty.databinding.FragmentLocationInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationInfoFragment : Fragment() {
    private lateinit var binding:FragmentLocationInfoBinding
    private val args:LocationInfoFragmentArgs by navArgs()
    private val vModel:LocationInfoViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationInfoBinding.inflate(layoutInflater,container,false)
        if (args.id == -1) initUi(args.location?:throw NullPointerException("fragment require id or location"))
        else {
            vModel.locationLiveData.observe(viewLifecycleOwner){
                initUi(it)
            }
            vModel.loadLocationById(args.id)
        }
        return binding.root
    }
    @SuppressLint("SetTextI18n")
    private fun initUi(notNullLocation:Location){
        vModel.listLiveData.observe(viewLifecycleOwner){ it ->
            val listOfNames = it.map { item-> item.name }
            binding.listOfResidents.adapter =
                ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,listOfNames)
            binding.listOfResidents.setOnItemClickListener { _, _, position, _ ->
                val dir = LocationInfoFragmentDirections.actionLocationInfoFragmentToCharacterInfoFragment(it[position],null)
                findNavController().navigate(dir)
            }
            binding.progress.visibility = View.GONE

        }
        notNullLocation.residents?.toList()?.let { vModel.load(it) }
        binding.name.text = "Название местоположения:\n" + notNullLocation.name
        binding.type.text= "Тип местоположения:\n" + notNullLocation.type
        binding.dimension.text = "Измерение:\n" + notNullLocation.dimension
    }
}