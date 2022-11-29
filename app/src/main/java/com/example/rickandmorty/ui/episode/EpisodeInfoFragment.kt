package com.example.rickandmorty.ui.episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.rickandmorty.data.model.Episode
import com.example.rickandmorty.databinding.FragmentEpisodeInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeInfoFragment : Fragment() {
    private lateinit var binding:FragmentEpisodeInfoBinding
    private val vModel by viewModels<EpisodeInfoViewModel>()
    private val args:EpisodeInfoFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeInfoBinding.inflate(inflater,container,false)
        if (args.id != -1) {
            vModel.getEpisode(args.id)
            vModel.episodeLiveData.observe(viewLifecycleOwner){
                initUi(it)
            }
        }else {
            initUi(args.episode ?: throw NullPointerException("require id or episode"))
        }

        return binding.root
    }
    private fun initUi(notNullEpisode:Episode){
        binding.name.text = notNullEpisode.name
        binding.episode.text = notNullEpisode.codeOfEpisode
        binding.airDate.text = notNullEpisode.airDate

        vModel.getMultipleCharacters(notNullEpisode.characters!!)
        vModel.entityLiveData.observe(viewLifecycleOwner){
            val b = it.map {tyt-> tyt.name }
            binding.listOfCharacters.adapter =
                ArrayAdapter(requireContext(),
                    android.R.layout.simple_list_item_1,
                    b)
            binding.load.visibility = View.GONE
            binding.listOfCharacters.setOnItemClickListener { _, _, position, _ ->
                val dir = EpisodeInfoFragmentDirections.actionEpisodeInfoFragmentToCharacterInfoFragment(it[position])
                findNavController().navigate(dir)
            }
        }

    }

}