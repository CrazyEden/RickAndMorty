package com.example.rickandmorty.ui.characterinfo

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.example.rickandmorty.R
import com.example.rickandmorty.data.model.Entity
import com.example.rickandmorty.databinding.FragmentCharacterInfoBinding
import com.example.rickandmorty.ui.episode.EpisodeInfoFragment


class CharacterInfoFragment : Fragment() {
    lateinit var binding: FragmentCharacterInfoBinding

    private lateinit var species:String
    private lateinit var type:String
    private lateinit var nameOri:String
    private lateinit var nameLoc:String
    private lateinit var created:String
    private lateinit var entity: Entity
    override fun onCreate(savedInstanceState: Bundle?) {
        entity = if (SDK_INT >= 33) arguments?.getParcelable(ENTITY_KEY,Entity::class.java) ?: throw NullPointerException("for fragment require entity")
        else (@Suppress("DEPRECATION") (arguments?.getParcelable(ENTITY_KEY)) as? Entity) ?: throw NullPointerException("for fragment require entity")

        species = getString(R.string.species) + entity.species
        type = getString(R.string.type) + entity.type
        nameOri = getString(R.string.name_origin) + entity.origin?.name
        nameLoc = getString(R.string.last_known_location) + entity.location?.name
        created = getString(R.string.created_at) + stringToDate(entity.created!!)

        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterInfoBinding.inflate(inflater,container,false)
        binding.imageView.load(entity.image){
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
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container_view_tag, EpisodeInfoFragment.newInstanceById(episodes[position].toInt()))
                .commit()
        }

        return binding.root
    }



    companion object {
        const val ENTITY_KEY = "entity"
        fun newInstanceByEntity(bundleWithEntity: Bundle) = CharacterInfoFragment().apply {
            arguments = bundleWithEntity
        }
    }
}
fun stringToDate(string: String): String {
    val temp = string.split("T")
    val day = temp[0].split("-")
    return "${day[2]}.${day[1]}.${day[0]} | ${temp[1].split(".")[0].dropLast(3)}"
}