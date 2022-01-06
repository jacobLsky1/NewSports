package com.jacoblip.andriod.newsports.ui.leagues.fragments.topscores

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jacoblip.andriod.newsports.data.services.viewmodels.LeaguesViewModel
import com.jacoblip.andriod.newsports.databinding.SelectedLeagueSeasonTopCardsFragmentBinding
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.TopCardScorerAdapter
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.TopGoalScorerAdapter


class SeasonCardScorersFragment():Fragment() {

    lateinit var binding: SelectedLeagueSeasonTopCardsFragmentBinding
    lateinit var viewModel: LeaguesViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SelectedLeagueSeasonTopCardsFragmentBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root
        view.apply {
            binding.cardsRV.layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel = ViewModelProvider(requireActivity()).get(LeaguesViewModel::class.java)
        setUpObservers()
        return view
    }

    fun setUpObservers(){
        viewModel.topScorers.observe(viewLifecycleOwner,{
            if(it!=null){
                binding.textView4.isVisible = false
                binding.cardsRV.isVisible = true
                binding.cardsRV.adapter = TopCardScorerAdapter(it.cardscorers.data)
            }else{
                binding.textView4.isVisible = true
                binding.cardsRV.isVisible = false
            }
        })
    }

    companion object{
        fun newInstance():SeasonCardScorersFragment {
            return SeasonCardScorersFragment()
        }
    }

}