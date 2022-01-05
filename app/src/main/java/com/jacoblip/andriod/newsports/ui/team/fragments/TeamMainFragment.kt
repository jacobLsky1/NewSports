package com.jacoblip.andriod.newsports.ui.team.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.jacoblip.andriod.newsports.data.models.team.Team
import com.jacoblip.andriod.newsports.data.services.viewmodels.TeamViewModel
import com.jacoblip.andriod.newsports.databinding.TeamMainFragmentBinding
import com.jacoblip.andriod.newsports.ui.adapters.pager_adapters.SelectedMatchHomeFragmentsAdapter
import com.jacoblip.andriod.newsports.ui.adapters.pager_adapters.SelectedTeamHomeFragmentsAdapter


class TeamMainFragment(var team:Team):Fragment() {

    lateinit var binding: TeamMainFragmentBinding
    lateinit var viewModel: TeamViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TeamMainFragmentBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root
        view.apply {

        }
        viewModel = ViewModelProvider(requireActivity()).get(TeamViewModel::class.java)
        setUpObservers()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewpager.adapter = SelectedTeamHomeFragmentsAdapter(childFragmentManager,requireContext(),team)
        binding.tabLayout.setupWithViewPager(binding.viewpager)
        binding.viewpager.isVisible = true
        binding.tabLayout.isVisible = true
        binding.viewpager.offscreenPageLimit = 4
        val scale = resources.displayMetrics.density
        binding.tabLayout.layoutParams.height = (36 * scale + 0.5F).toInt()
        binding.tabLayout.requestLayout()
        binding.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
    }

    fun setUpObservers(){

    }

    companion object{
        fun newInstance(team:Team):TeamMainFragment {
            return TeamMainFragment(team)
        }
    }

}