package com.example.rickandmorty.fragments.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.rickandmorty.databinding.FragmentInfoBinding


class InfoFragment : Fragment() {
    lateinit var binding: FragmentInfoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoBinding.inflate(inflater,container,false)
        val name = "Имя: " + arguments?.getString(NAME_KEY)
        val status = "Статус: " + arguments?.getString(STATUS_KEY)
        val species = "Вид: " + arguments?.getString(SPECIES_KEY)
        val typeArg = arguments?.getString(TYPE_KEY)
        val type = "Подвид: $typeArg"
        val gender = "Гендер: " + arguments?.getString(GENDER_KEY)
        val nameOri = "Родное место: " + arguments?.getString(NAMEORI_KEY)
        val nameLoc = "Последнее местоположение: " + arguments?.getString(NAMELOC_KEY)
        val created = "Дата появления в данной базе данных: " + arguments?.getString(CREATED_KEY)

        binding.imageView.load(arguments?.getString(IMAGEVIEW_KEY))
        binding.name.text = name
        binding.status.text = status
        binding.species.text = species
        if (typeArg.isNullOrEmpty()) {
            binding.type.visibility = View.GONE
        }else binding.type.text = type
        binding.gender.text = gender
        binding.originName.text = nameOri
        binding.location.text = nameLoc
        binding.created.text = created


        return binding.root
    }
    companion object {
        const val IMAGEVIEW_KEY = "key_imageView"
        const val NAME_KEY = "key_name"
        const val STATUS_KEY = "key_status"
        const val SPECIES_KEY = "key_species"
        const val TYPE_KEY = "key_type"
        const val GENDER_KEY = "key_gender"
        const val NAMEORI_KEY = "key_nameOri"
        const val NAMELOC_KEY = "key_nameLoc"
        const val CREATED_KEY = "key_created"


        fun newInstance(bundle: Bundle) = InfoFragment().apply {
            arguments = bundle
        }
    }
}