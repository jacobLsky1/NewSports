package com.jacoblip.andriod.newsports.ui.leagues.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.jacoblip.andriod.newsports.data.models.leagues.Coverage
import com.jacoblip.andriod.newsports.data.models.leagues.CustomLeague
import com.jacoblip.andriod.newsports.data.services.viewmodels.LeaguesViewModel
import com.jacoblip.andriod.newsports.data.services.viewmodels.MainViewModel
import com.jacoblip.andriod.newsports.databinding.SelectedLeagueFragmentHomeBinding
import com.jacoblip.andriod.newsports.ui.adapters.pager_adapters.SelectedLeagueFragmentsAdapter
import com.jacoblip.andriod.newsports.ui.adapters.pager_adapters.SelectedMatchHomeFragmentsAdapter


class SelectedLeagueMainFragment(val seasonId: Long,val coverage: Coverage,val hasStandings:Boolean):Fragment() {

    lateinit var binding: SelectedLeagueFragmentHomeBinding
    lateinit var viewModel: LeaguesViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SelectedLeagueFragmentHomeBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root
        view.apply {

        }
        setUpServices()
        setUpObservers()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewpager.adapter = SelectedLeagueFragmentsAdapter(childFragmentManager,seasonId,coverage,hasStandings)
        binding.tabLayout.setupWithViewPager(binding.viewpager)
        binding.viewpager.isVisible = true
        binding.tabLayout.isVisible = true
        binding.viewpager.offscreenPageLimit = 2


    }

    fun setUpObservers(){

    }

    private fun setUpServices(){
        viewModel = ViewModelProvider(requireActivity()).get(LeaguesViewModel::class.java)
        viewModel.getTopScorersForSeason(seasonId)
    }

    companion object{
        fun newInstance( leagueId: Long, coverage: Coverage,hasStandings:Boolean):SelectedLeagueMainFragment {
            return SelectedLeagueMainFragment(leagueId,coverage,hasStandings)
        }
    }

}