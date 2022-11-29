package com.example.rickandmorty.ui.characterinfo

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.example.rickandmorty.R
import com.example.rickandmorty.data.model.Entity
import com.example.rickandmorty.databinding.FragmentCharacterInfoBinding


class CharacterInfoFragment : Fragment() {
    lateinit var binding: FragmentCharacterInfoBinding

    private lateinit var species:String
    private lateinit var type:String
    private lateinit var nameOri:String
    private lateinit var nameLoc:String
    private lateinit var created:String
    private lateinit var entity: Entity
    private val args:CharacterInfoFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        entity = args.entity

        species = getString(R.string.species) + entity.species
        type = getString(R.string.type) + entity.type
        nameOri = getString(R.string.name_origin) + entity.origin?.name
        nameLoc = getString(R.string.last_known_location) + entity.location?.name
        created = getString(R.string.created_at) + stringToDate(entity.created!!)

        TransitionInflater.from(context).inflateTransition(android.R.transition.move).apply {
            sharedElementEnterTransition = this
            duration = 400
        }


        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterInfoBinding.inflate(inflater,container,false)
        binding.avatarkaInfo.transitionName = entity.id.toString()

        binding.avatarkaInfo.load(args.bitmap?: entity.image){
            crossfade(true)
            transformations(CircleCropTransformation())
        }
        binding.name.text = entity.name
        binding.status.text = entity.status
        binding.species.text = species
        if (!entity.type.isNullOrEmpty()) {
            binding.type.visibility = View.VISIBLE
            binding.type.text = type
        }
        binding.genderView.load(when(entity.gender){
            "Male"->R.drawable.ic_male
            "Female"->R.drawable.ic_female
            else ->R.drawable.ic_unknow_gender
        })
        binding.originName.text = nameOri
        binding.location.text = nameLoc
        binding.created.text = created

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
fun stringToDate(string: String): String {
    val temp = string.split("T")
    val day = temp[0].split("-")
    return "${day[2]}.${day[1]}.${day[0]} | ${temp[1].split(".")[0].dropLast(3)}"
}