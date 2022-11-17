package com.example.rickandmorty.fragments.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.rickandmorty.databinding.FragmentInfoBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
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
        val name = "Имя: " +requireArguments().getString("name")
        val status = "Статус: " + requireArguments().getString("status")
        val species = "Вид: " + requireArguments().getString("species")
        val type = "Подвид: " + requireArguments().getString("type")
        val gender = "Гендер: " + requireArguments().getString("gender")
        val nameOri = "Родное место: " + requireArguments().getString("nameOri")
        val nameLoc = "Последнее местоположение: " + requireArguments().getString("nameLoc")
        val created = "Дата появления в данной базе данных: " + requireArguments().getString("created")

        binding.imageView.load(requireArguments().getString("imageView"))
        binding.name.text = name
        binding.status.text = status
        binding.species.text = species
        binding.type.text = type
        binding.gender.text = gender
        binding.originName.text = nameOri
        binding.location.text = nameLoc
        binding.created.text = created


        return binding.root
    }

}