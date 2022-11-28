package com.example.rickandmorty.ui.episode

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.rickandmorty.R
import com.example.rickandmorty.data.model.Episode
import com.example.rickandmorty.databinding.FragmentEpisodeInfoBinding
import com.example.rickandmorty.ui.characterinfo.CharacterInfoFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeInfoFragment : Fragment() {
    private lateinit var binding:FragmentEpisodeInfoBinding
    private val vModel by viewModels<EpisodeInfoViewModel>()
    private var id: Int? = null
    private var episode: Episode? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        id = arguments?.getInt(ID_KEY)
        episode = if (SDK_INT >= 33) arguments?.getParcelable(EPISODE_KEY,Episode::class.java)
        else (@Suppress("DEPRECATION") arguments?.getParcelable(EPISODE_KEY)) as? Episode
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeInfoBinding.inflate(inflater,container,false)
        if (id != 0) {
            vModel.getEpisode(id!!)
            vModel.episodeLiveData.observe(viewLifecycleOwner){
                initUi(it)
            }
        }else {
            initUi(episode ?: throw NullPointerException("for the fragment require episode or id"))
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
                val bundle = bundleOf(CharacterInfoFragment.ENTITY_KEY to it[position])
                parentFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragment_container_view_tag, CharacterInfoFragment.newInstanceByEntity(bundle))
                    .commit()
            }
        }

    }
    companion object {
        const val EPISODE_KEY = "episode"
        const val ID_KEY = "id"

        fun newInstanceByEpisode(bundleWithOneEpisode: Bundle) = EpisodeInfoFragment().apply {
            arguments = bundleWithOneEpisode
        }

        fun newInstanceById(id:Int) = EpisodeInfoFragment().apply {
            arguments = bundleOf( ID_KEY to id)
        }

    }
}