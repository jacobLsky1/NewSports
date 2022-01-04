package com.jacoblip.andriod.newsports.ui.match.fragmnets

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
import com.jacoblip.andriod.newsports.databinding.SelectedMatchStandingsBinding
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.StandingsAdapter
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.StandingsAdapter1


class SelectedMatchStandingsFragment(var match:Fixture):Fragment() {

    lateinit var binding: SelectedMatchStandingsBinding
    lateinit var viewModel: MatchViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SelectedMatchStandingsBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root
        viewModel = ViewModelProvider(requireActivity()).get(MatchViewModel::class.java)
        viewModel.getMatchStandings(match.season_id)
        setUpObservers()
        return view
    }

    fun setUpObservers(){
        viewModel.matchStandings.observe(viewLifecycleOwner,{
            if(it.isNotEmpty()){
                binding.standingsRV.isVisible = true
                binding.NoStandingsToShowYet.isVisible = false
                binding.standingsRV.layoutManager = LinearLayoutManager(context)
                binding.standingsRV.adapter = StandingsAdapter(it[0].standings.data,requireContext(), match.localteam_id, match.visitorteam_id)
            }else{
                binding.standingsRV.isVisible = false
                binding.NoStandingsToShowYet.isVisible = true
            }
        })

        viewModel.isFetchingData.observe(viewLifecycleOwner,{
            binding.progressBar.isVisible = it
        })
    }

    companion object{
        fun newInstance(match: Fixture):SelectedMatchStandingsFragment {
            return SelectedMatchStandingsFragment(match)
        }
    }

}