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
import com.jacoblip.andriod.newsports.databinding.SelectedLeagueSeasonUpcomingMatchsBinding
import com.jacoblip.andriod.newsports.interfaces.upcoming
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.leages.LeagueMatchesUpcomingAdapter
import com.jacoblip.andriod.newsports.utilities.Util
import com.jacoblip.andriod.newsports.utilities.queryLimit


class SeasonUpcomingMatchesFragment(val seasonId:Long):Fragment() {

    lateinit var binding: SelectedLeagueSeasonUpcomingMatchsBinding
    lateinit var viewModel: LeaguesViewModel
    private var currentPage = 1
    var url = ""



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SelectedLeagueSeasonUpcomingMatchsBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root
        view.apply {
            binding.upcomingMatchesRV.layoutManager = LinearLayoutManager(requireContext())
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
        url = "seasons/$seasonId/?api_token=${Util.API}&include=$upcoming,round"

        if(isLoadingMore){

        }else{

        }

        viewModel.getFutureSeasonMatches(url)
    }

    fun setUpObservers(){
        viewModel.futureSeasonMatches.observe(viewLifecycleOwner,{
            if(!it.isNullOrEmpty()){
                binding.upcomingMatchesRV.isVisible = true
                binding.textView3.isVisible = false
                binding.upcomingMatchesRV.adapter = LeagueMatchesUpcomingAdapter(it,requireContext(),false)
            }else{
                binding.upcomingMatchesRV.isVisible = false
                binding.textView3.isVisible = true
            }
        })

        Util.requestTryAgain.observe(viewLifecycleOwner,{
            if(it==14){
                viewModel.getFutureSeasonMatches(url)
                Util.requestTryAgain.postValue(0)
            }
        })

    }

    companion object{
        fun newInstance(seasonId:Long):SeasonUpcomingMatchesFragment {
            return SeasonUpcomingMatchesFragment(seasonId)
        }
    }

}