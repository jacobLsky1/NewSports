package com.jacoblip.andriod.newsports.ui.leagues.fragments.topscores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jacoblip.andriod.newsports.data.models.leagues.Coverage
import com.jacoblip.andriod.newsports.data.services.viewmodels.LeaguesViewModel
import com.jacoblip.andriod.newsports.databinding.SelectedLeagueFragmentTopScorersBinding
import com.jacoblip.andriod.newsports.ui.adapters.pager_adapters.SeasonTopScorersFragmentsAdapter


class SelectedLeagueTopScorersFragment(val leagueId: Long,val coverage: Coverage):Fragment() {

    lateinit var binding: SelectedLeagueFragmentTopScorersBinding
    lateinit var viewModel: LeaguesViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SelectedLeagueFragmentTopScorersBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root
        view.apply {

        }
        viewModel = ViewModelProvider(requireActivity()).get(LeaguesViewModel::class.java)
        setUpObservers()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewpager.adapter = SeasonTopScorersFragmentsAdapter(childFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewpager)
        binding.viewpager.offscreenPageLimit = 2
    }

    fun setUpObservers(){

    }

    companion object{
        fun newInstance(leagueId: Long, coverage: Coverage): SelectedLeagueTopScorersFragment {
            return SelectedLeagueTopScorersFragment(leagueId,coverage)
        }
    }

}