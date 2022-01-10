package com.jacoblip.andriod.newsports.ui.leagues.fragments.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture
import com.jacoblip.andriod.newsports.data.services.viewmodels.LeaguesViewModel
import com.jacoblip.andriod.newsports.databinding.SelectedLeagueSeasonRecentMatchsBinding
import com.jacoblip.andriod.newsports.interfaces.results
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.leages.LeagueMatchesResultsAdapter
import com.jacoblip.andriod.newsports.utilities.Util
import com.jacoblip.andriod.newsports.utilities.queryLimit


class SeasonResultMatchesFragment(val seasonId:Long):Fragment() {

    lateinit var binding: SelectedLeagueSeasonRecentMatchsBinding
    lateinit var viewModel: LeaguesViewModel
    private var currentPage = 1
    var url = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SelectedLeagueSeasonRecentMatchsBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root
        view.apply {
            binding.recentMatchesRV.layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel = ViewModelProvider(requireActivity()).get(LeaguesViewModel::class.java)
        setUpObservers()
        loadData(currentPage,false)
        binding.baseLoadMoreLayout.setOnClickListener {
            loadData(currentPage,true)
        }
        return view
    }

    fun setUpObservers(){
        viewModel.pastSeasonMatches.observe(viewLifecycleOwner,{
            if(!it.isNullOrEmpty()){
                binding.recentMatchesRV.isVisible = true
                binding.textView12.isVisible = false
                binding.recentMatchesRV.adapter = LeagueMatchesResultsAdapter(it,requireContext(),false)
            }else{
                binding.recentMatchesRV.isVisible = false
                binding.textView12.isVisible = true
            }
        })

        Util.requestTryAgain.observe(viewLifecycleOwner ,{
            if(it==13){
                viewModel.getFutureSeasonMatches(url)
                Util.requestTryAgain.postValue(0)
            }
        })

    }

    fun loadData(page: Int, isLoadingMore: Boolean) {
        currentPage += 1
        url = "seasons/$seasonId/?api_token=${Util.API}&include=$results,round"

        if(isLoadingMore){

        }else{

        }
        viewModel.getPassedSeasonMatches(url)
    }

    companion object{
        fun newInstance( seasonId:Long):SeasonResultMatchesFragment {
            return SeasonResultMatchesFragment(seasonId)
        }
    }

}