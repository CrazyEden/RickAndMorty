package com.example.rickandmorty.fragments.info

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.rickandmorty.R
import com.example.rickandmorty.data.model.Entity
import com.example.rickandmorty.databinding.FragmentInfoBinding


class InfoFragment : Fragment() {
    lateinit var binding: FragmentInfoBinding
    private var name:String? = null
    private var status:String? = null
    private var species:String? = null
    private var type:String? = null
    private var gender:String? = null
    private var nameOri:String? = null
    private var nameLoc:String? = null
    private var created:String? = null
    private var entity: Entity? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        entity = if (SDK_INT >= 33) arguments?.getParcelable(ENTITY_KEY,Entity::class.java)
        else  @Suppress("DEPRECATION") arguments?.getParcelable(ENTITY_KEY) as? Entity

        name = getString(R.string.name) + entity?.name
        status = getString(R.string.status) + entity?.status
        species = getString(R.string.species) + entity?.species
        type = getString(R.string.type) + entity?.type
        gender = getString(R.string.gender) + entity?.gender
        nameOri = getString(R.string.name_origin) + entity?.origin?.name
        nameLoc = getString(R.string.last_known_location) + entity?.location?.name
        created = getString(R.string.created_at) + entity?.created

        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoBinding.inflate(inflater,container,false)
        binding.imageView.load(entity?.image)
        binding.name.text = name
        binding.status.text = status
        binding.species.text = species
        if (!entity?.type.isNullOrEmpty()) {
            binding.type.visibility = View.VISIBLE
            binding.type.text = type
        }
        binding.gender.text = gender
        binding.originName.text = nameOri
        binding.location.text = nameLoc
        binding.created.text = created

        return binding.root
    }
    companion object {
        const val ENTITY_KEY = "entity"
        fun newInstance(bundle: Bundle) = InfoFragment().apply {
            arguments = bundle
        }
    }
}