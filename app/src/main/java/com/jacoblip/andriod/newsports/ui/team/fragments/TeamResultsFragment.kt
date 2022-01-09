package com.jacoblip.andriod.newsports.ui.team.fragments

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
import com.jacoblip.andriod.newsports.databinding.TeamResultsFragmentBinding
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.matches.MatchesLiteAdapter
import com.jacoblip.andriod.newsports.utilities.Util


class TeamResultsFragment(val team:Team):Fragment() {

    lateinit var binding: TeamResultsFragmentBinding
    lateinit var viewModel: TeamViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TeamResultsFragmentBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root
        view.apply {
            binding.teamResultsRV.layoutManager = LinearLayoutManager(context)
            binding.teamResultsRV.isNestedScrollingEnabled = true
        }
        viewModel = ViewModelProvider(requireActivity()).get(TeamViewModel::class.java)
        viewModel.fetchResultsForTeam(team.id)
        setUpObservers()
        return view
    }

    fun setUpObservers(){
        viewModel.allTeamMatches.observe(viewLifecycleOwner,{
            if(!it.isNullOrEmpty()){
                binding.noDataTV.isVisible = false
                binding.teamResultsRV.isVisible = true
                binding.teamResultsRV.adapter = MatchesLiteAdapter(it,team.id,true,requireContext())

            }else{
                binding.noDataTV.isVisible = true
                binding.teamResultsRV.isVisible = false
            }
        })

        Util.requestTryAgain.observe(viewLifecycleOwner,{
            if(it==5){
                viewModel.fetchResultsForTeam(team.id)
            }
        })

        viewModel.isFetchingData.observe(viewLifecycleOwner,{
            binding.progressBar5.isVisible = it
        })
    }

    companion object{
        fun newInstance(team:Team):TeamResultsFragment {
            return TeamResultsFragment(team)
        }
    }

}