package com.jacoblip.andriod.newsports.ui.leagues.fragments.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture
import com.jacoblip.andriod.newsports.data.services.viewmodels.LeaguesViewModel
import com.jacoblip.andriod.newsports.databinding.SelectedLeagueSeasonRecentMatchsBinding
import com.jacoblip.andriod.newsports.databinding.SelectedLeagueSeasonUpcomingMatchsBinding
import com.jacoblip.andriod.newsports.interfaces.upcoming
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.LeagueMatchesResultsAdapter
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.LeagueMatchesUpcomingAdapter
import com.jacoblip.andriod.newsports.utilities.API_KEY
import com.jacoblip.andriod.newsports.utilities.queryLimit


class SeasonUpcomingMatchesFragment(val seasonId:Long):Fragment() {

    lateinit var binding: SelectedLeagueSeasonUpcomingMatchsBinding
    lateinit var viewModel: LeaguesViewModel
    private var currentPage = 1
    private var matches: ArrayList<Fixture> = arrayListOf()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SelectedLeagueSeasonUpcomingMatchsBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root
        view.apply {
            binding.MatchesRV.layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel = ViewModelProvider(requireActivity()).get(LeaguesViewModel::class.java)
        setUpObservers()
        loadData(currentPage,false)
        binding.baseLoadMoreLayout.setOnClickListener {
            loadData(currentPage,true)
        }
        return view
    }

    fun loadData(page: Int, isLoadingMore: Boolean) {
        currentPage += 1
        val url = "seasons/$seasonId/?api_token=$API_KEY&include=upcoming:limit($queryLimit|$page),$upcoming,round"

        if(isLoadingMore){

        }else{

        }

        viewModel.getFutureSeasonMatches(url)
    }

    fun setUpObservers(){
        viewModel.futureSeasonMatches.observe(viewLifecycleOwner,{
            if(!it.isNullOrEmpty()){
                binding.MatchesRV.adapter = LeagueMatchesUpcomingAdapter(it,requireContext(),false)
            }else{

            }
        })
    }

    companion object{
        fun newInstance(seasonId:Long):SeasonUpcomingMatchesFragment {
            return SeasonUpcomingMatchesFragment(seasonId)
        }
    }

}