package com.jacoblip.andriod.newsports.ui.team.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jacoblip.andriod.newsports.data.models.seasons.players.PlayerRanking
import com.jacoblip.andriod.newsports.data.models.team.Team
import com.jacoblip.andriod.newsports.data.services.viewmodels.TeamViewModel
import com.jacoblip.andriod.newsports.databinding.TeamOverviewFragmentBinding
import com.jacoblip.andriod.newsports.databinding.TeamResultsFragmentBinding
import com.jacoblip.andriod.newsports.databinding.TeamSquadFragmentBinding
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.MatchesLiteAdapter
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.TeamSquadAdapter
import com.jacoblip.andriod.newsports.utilities.Util


class TeamSquadFragment(val team:Team):Fragment() {

    lateinit var binding: TeamSquadFragmentBinding
    lateinit var viewModel: TeamViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TeamSquadFragmentBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root
        view.apply {
            binding.playersRV.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        }
        viewModel = ViewModelProvider(requireActivity()).get(TeamViewModel::class.java)
        viewModel.fetchSquadForTeam(team.id)
        setUpObservers()
        return view
    }

    fun setUpObservers(){
        viewModel.teamSquad.observe(viewLifecycleOwner,{
            if(!it.isNullOrEmpty()){
                binding.noDataTV.isVisible = false
                binding.playersRV.isVisible = true
                binding.playersRV.adapter = TeamSquadAdapter(it)

            }else{
                binding.noDataTV.isVisible = true
                binding.playersRV.isVisible = false
            }
        })

        Util.requestTryAgain.observe(viewLifecycleOwner,{
            if(it==7){
                viewModel.fetchSquadForTeam(team.id)
            }
        })

        viewModel.isFetchingData.observe(viewLifecycleOwner,{
            binding.progressBar4.isVisible = it
        })
    }

    companion object{
        fun newInstance(team:Team):TeamSquadFragment {
            return TeamSquadFragment(team)
        }
    }

}