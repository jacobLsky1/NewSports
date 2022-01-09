package com.jacoblip.andriod.newsports.ui.leagues.fragments.topscores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jacoblip.andriod.newsports.data.services.viewmodels.LeaguesViewModel
import com.jacoblip.andriod.newsports.databinding.SelectedLeagueSeasonTopAssistsFragmentBinding
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.leages.TopAssistsScorerAdapter


class SeasonAssistScorersFragment():Fragment() {

    lateinit var binding: SelectedLeagueSeasonTopAssistsFragmentBinding
    lateinit var viewModel: LeaguesViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SelectedLeagueSeasonTopAssistsFragmentBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root
        view.apply {
            binding.assistsRV.layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel = ViewModelProvider(requireActivity()).get(LeaguesViewModel::class.java)
        setUpObservers()
        return view
    }

    fun setUpObservers(){
        viewModel.topScorers.observe(viewLifecycleOwner,{
            if(!it.assistscorers.data.isNullOrEmpty()){
                binding.textView4.isVisible = false
                binding.assistsRV.isVisible = true
                binding.assistsRV.adapter = TopAssistsScorerAdapter(it.assistscorers.data)
            }else{
                binding.textView4.isVisible = true
                binding.assistsRV.isVisible = false
            }
        })
    }

    companion object{
        fun newInstance():SeasonAssistScorersFragment {
            return SeasonAssistScorersFragment()
        }
    }

}