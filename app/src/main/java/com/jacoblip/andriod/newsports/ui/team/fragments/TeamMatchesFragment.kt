package com.jacoblip.andriod.newsports.ui.team.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jacoblip.andriod.newsports.data.models.team.Team
import com.jacoblip.andriod.newsports.data.services.viewmodels.TeamViewModel
import com.jacoblip.andriod.newsports.databinding.TeamMatchesFragmentBinding
import com.jacoblip.andriod.newsports.databinding.TeamOverviewFragmentBinding
import com.jacoblip.andriod.newsports.databinding.TeamResultsFragmentBinding
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.MatchesLiteAdapter
import com.jacoblip.andriod.newsports.utilities.Util


class TeamMatchesFragment(val team:Team):Fragment() {

    lateinit var binding: TeamMatchesFragmentBinding
    lateinit var viewModel: TeamViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TeamMatchesFragmentBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root
        view.apply {
            binding.teamUpcomingMatchesRV.layoutManager = LinearLayoutManager(context)
            binding.teamUpcomingMatchesRV.isNestedScrollingEnabled = true
        }
        viewModel = ViewModelProvider(requireActivity()).get(TeamViewModel::class.java)
        viewModel.fetchUpcomingMatchesForTeam(team.id)
        setUpObservers()
        return view
    }

    fun setUpObservers(){
        viewModel.upcomingTeamMatches.observe(viewLifecycleOwner,{
            if(!it.isNullOrEmpty()){
                binding.noDataTV.isVisible = false
                binding.teamUpcomingMatchesRV.isVisible = true
                binding.teamUpcomingMatchesRV.adapter = MatchesLiteAdapter(it,team.id,true,requireContext())

            }else{
                binding.noDataTV.isVisible = true
                binding.teamUpcomingMatchesRV.isVisible = false
            }
        })

        Util.requestTryAgain.observe(viewLifecycleOwner,{
            if(it==6){
                viewModel.fetchUpcomingMatchesForTeam(team.id)
            }
        })
        viewModel.isFetchingData.observe(viewLifecycleOwner,{
            binding.progressBar6.isVisible = it
        })
    }

    companion object{
        fun newInstance(team:Team):TeamMatchesFragment {
            return TeamMatchesFragment(team)
        }
    }

}