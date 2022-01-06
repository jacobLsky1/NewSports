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
import com.jacoblip.andriod.newsports.databinding.SelectedLeagueSeasonTopGoalsFragmentBinding
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.TopGoalScorerAdapter


class SeasonGoalScorersFragment():Fragment() {

    lateinit var binding: SelectedLeagueSeasonTopGoalsFragmentBinding
    lateinit var viewModel: LeaguesViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SelectedLeagueSeasonTopGoalsFragmentBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root
        view.apply {
            binding.goalsRV.layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel = ViewModelProvider(requireActivity()).get(LeaguesViewModel::class.java)
        setUpObservers()
        return view
    }

    fun setUpObservers(){
        viewModel.topScorers.observe(viewLifecycleOwner,{
            if(it!=null){
                binding.textView4.isVisible = false
                binding.goalsRV.isVisible = true
                binding.goalsRV.adapter = TopGoalScorerAdapter(it.goalscorers.data)
            }else{
                binding.textView4.isVisible = true
                binding.goalsRV.isVisible = false
            }
        })
    }

    companion object{
        fun newInstance():SeasonGoalScorersFragment {
            return SeasonGoalScorersFragment()
        }
    }

}