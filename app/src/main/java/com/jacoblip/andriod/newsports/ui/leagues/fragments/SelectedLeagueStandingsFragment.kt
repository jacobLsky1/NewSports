package com.jacoblip.andriod.newsports.ui.leagues.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jacoblip.andriod.newsports.data.models.leagues.Coverage
import com.jacoblip.andriod.newsports.data.models.leagues.CustomLeague
import com.jacoblip.andriod.newsports.data.services.viewmodels.LeaguesViewModel
import com.jacoblip.andriod.newsports.databinding.SelectedLeagueFragmentStandingsBinding
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.StandingsAdapter


class SelectedLeagueStandingsFragment(val leagueId: Long,val coverage: Coverage):Fragment() {

    lateinit var binding: SelectedLeagueFragmentStandingsBinding
    lateinit var viewModel: LeaguesViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SelectedLeagueFragmentStandingsBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root
        view.apply {
            binding.standingsRV.layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel = ViewModelProvider(requireActivity()).get(LeaguesViewModel::class.java)
        setUpObservers()
        viewModel.getStandingsForSeason(leagueId)
        return view
    }

    fun setUpObservers(){
        viewModel.standings.observe(viewLifecycleOwner,{
            if(!it.isNullOrEmpty()){
                binding.standingsRV.adapter = StandingsAdapter(it[0].standings.data, requireContext(), 0L, 0L)
            }
        })
    }

    companion object{
        fun newInstance(leagueId: Long, coverage: Coverage):SelectedLeagueStandingsFragment {
            return SelectedLeagueStandingsFragment(leagueId,coverage)
        }
    }

}