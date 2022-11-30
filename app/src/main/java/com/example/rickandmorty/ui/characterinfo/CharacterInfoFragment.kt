package com.example.rickandmorty.ui.characterinfo

import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.rickandmorty.R
import com.example.rickandmorty.data.model.Entity
import com.example.rickandmorty.databinding.FragmentCharacterInfoBinding


class CharacterInfoFragment : Fragment() {
    private lateinit var binding: FragmentCharacterInfoBinding

    private lateinit var entity: Entity
    private val args:CharacterInfoFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        entity = args.entity
        TransitionInflater.from(context).inflateTransition(android.R.transition.move).apply {
            sharedElementEnterTransition = this
            duration = 400
        }
        super.onCreate(savedInstanceState)
    }
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterInfoBinding.inflate(inflater,container,false)
        binding.avatarkaInfo.transitionName = entity.id.toString()

        binding.avatarkaInfo.load(args.bitmap?: entity.image)
        binding.name.text = entity.name
        binding.status.text = entity.status
        binding.species.text = getString(R.string.species) + entity.species
        if (!entity.type.isNullOrEmpty()) {
            binding.type.visibility = View.VISIBLE
            binding.type.text = getString(R.string.type) + entity.type
        }
        binding.genderView.load(when(entity.gender){
            "Male"->R.drawable.ic_male
            "Female"->R.drawable.ic_female
            else ->R.drawable.ic_unknow_gender
        })
        binding.originName.text = getString(R.string.name_origin) + entity.origin?.name
        binding.originName.setOnClickListener {
            runCatching {
                val dir = CharacterInfoFragmentDirections.actionCharacterInfoFragmentToLocationInfoFragment(
                    id = entity.origin?.url?.split("/")?.get(5)!!.toInt())
                findNavController().navigate(dir)
            }.getOrElse { (Toast.makeText(context,getString(R.string.unknown_location),Toast.LENGTH_SHORT).show()) }
        }
        binding.location.text = getString(R.string.last_known_location) + entity.location?.name
        binding.location.setOnClickListener {
            runCatching {
                val dir = CharacterInfoFragmentDirections.actionCharacterInfoFragmentToLocationInfoFragment(
                    id = entity.location?.url?.split("/")?.get(5)!!.toInt())
                findNavController().navigate(dir)
            }.getOrElse { (Toast.makeText(context,getString(R.string.unknown_location),Toast.LENGTH_SHORT).show()) }
        }

        val episodes = mutableListOf<String>()
        entity.episode?.forEach { episodes.add(it.split("/")[5]) }

        binding.grid.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,episodes)
        binding.grid.setOnItemClickListener { _, _, position, _ ->
            val dir = CharacterInfoFragmentDirections.actionCharacterInfoFragmentToEpisodeInfoFragment(episodes[position].toInt())
            findNavController().navigate(dir)
        }

        return binding.root
    }

}