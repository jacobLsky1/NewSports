package com.jacoblip.andriod.newsports.ui.match.fragmnets

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture
import com.jacoblip.andriod.newsports.data.services.viewmodels.MatchViewModel
import com.jacoblip.andriod.newsports.databinding.SelectedMatchLineupBinding
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.LineUpAdapter


class SelectedMatchLineUpFragment(var match:Fixture):Fragment() {

    lateinit var binding: SelectedMatchLineupBinding
    lateinit var viewModel: MatchViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SelectedMatchLineupBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root
        view.apply {

        }
        viewModel = ViewModelProvider(requireActivity()).get(MatchViewModel::class.java)
        setUpFragment()
        return view
    }

    private fun setUpFragment(){

        val localTeamLineup = match.lineup?.data?.filter { it.team_id == match.localteam_id }
        val visitingTeamLineup = match.lineup?.data?.filter { it.team_id == match.visitorteam_id }

        if(!localTeamLineup.isNullOrEmpty() && !visitingTeamLineup.isNullOrEmpty()) {
            binding.rvLayout.isVisible = true
            binding.noLineUp.isVisible = false
            val homeAdapter = LineUpAdapter(localTeamLineup)
            binding.homeLineUpRV.layoutManager = LinearLayoutManager(context)
            binding.homeLineUpRV.adapter = homeAdapter
            binding.homeLineUpRV.isNestedScrollingEnabled = true

            val awayAdapter = LineUpAdapter(visitingTeamLineup)
            binding.visitorLineUpRV.layoutManager = LinearLayoutManager(context)
            binding.visitorLineUpRV.adapter = awayAdapter
            binding.visitorLineUpRV.isNestedScrollingEnabled = true
        }else{
            binding.rvLayout.isVisible = false
            binding.noLineUp.isVisible = true
        }
    }

    companion object{
        fun newInstance(match: Fixture):SelectedMatchLineUpFragment {
            return SelectedMatchLineUpFragment(match)
        }
    }

}