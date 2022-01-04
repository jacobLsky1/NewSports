package com.jacoblip.andriod.newsports.ui.team.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jacoblip.andriod.newsports.data.models.team.Team
import com.jacoblip.andriod.newsports.data.services.viewmodels.TeamViewModel
import com.jacoblip.andriod.newsports.databinding.TeamOverviewFragmentBinding
import com.jacoblip.andriod.newsports.databinding.TeamResultsFragmentBinding
import com.jacoblip.andriod.newsports.databinding.TeamSquadFragmentBinding


class TeamSquadFragment(team:Team):Fragment() {

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

        }
        viewModel = ViewModelProvider(requireActivity()).get(TeamViewModel::class.java)
        setUpObservers()
        return view
    }

    fun setUpObservers(){

    }

    companion object{
        fun newInstance(team:Team):TeamSquadFragment {
            return TeamSquadFragment(team)
        }
    }

}