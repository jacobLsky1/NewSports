package com.jacoblip.andriod.newsports.ui.main.fragments.live

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.models.leagues.CustomLeague
import com.jacoblip.andriod.newsports.data.services.viewmodels.MainViewModel
import com.jacoblip.andriod.newsports.databinding.LeaguesFragmentHomeBinding
import com.jacoblip.andriod.newsports.databinding.LiveFragmentHomeBinding
import com.jacoblip.andriod.newsports.ui.adapters.pager_adapters.LiveHomeFragmentsAdapter
import com.jacoblip.andriod.newsports.ui.adapters.pager_adapters.MatchesHomeFragmentsAdapter
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.main.LiveSoccerMatchesAdapter
import com.jacoblip.andriod.newsports.utilities.Util
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class LiveMainFragment():Fragment(R.layout.live_fragment_home) {

    lateinit var viewModel: MainViewModel
    var swipeRefreshLayout: SwipeRefreshLayout? = null
    lateinit var binding: LiveFragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LiveFragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout!!.setOnRefreshListener {
           viewModel.loadLiveMatchesFromServer()
            swipeRefreshLayout!!.isRefreshing = false;
        }

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel.loadLiveMatchesFromServer()
        setUpObservers()
    }

    fun setUpObservers(){
        viewModel.listOfLiveMatches.observe(viewLifecycleOwner,{
            if(!it.isNullOrEmpty()) {
                binding.liveMatchesRV.isVisible = true
                binding.textView2.isVisible = false
                val leagues:List<CustomLeague> = sortLeagues(it)
                if(!leagues.isNullOrEmpty()){
                    binding.liveMatchesRV.isVisible = true
                    binding.textView2.isVisible = false
                    binding.liveMatchesRV.adapter = LiveSoccerMatchesAdapter(leagues, requireContext(), true)
                }else{
                    binding.liveMatchesRV.isVisible = false
                    binding.textView2.isVisible = true
                }

            }else{
                binding.liveMatchesRV.isVisible = false
                binding.textView2.isVisible = true
            }
        })

        Util.requestTryAgain.observe(viewLifecycleOwner,{
            if(it==15){
                viewModel.loadLiveMatchesFromServer()
            }
        })
    }

    private fun sortLeagues(leagues:List<CustomLeague>):List<CustomLeague>{
        var returnLeagues:ArrayList<CustomLeague> = arrayListOf()

        for(league in leagues){
            val fixtures = league.fixtures?.filter { it.time.status == "LIVE" || it.time.status == "HT" }
                ?.sortedBy { it.time.starting_at.timestamp }

            var returnleague = league
            returnleague.fixtures = fixtures

            if(!fixtures.isNullOrEmpty()){
                returnLeagues.add(returnleague)
            }
        }
        return returnLeagues
    }

    private fun filterMatches(leagues:List<CustomLeague>):List<CustomLeague>{
        var returnLeagues = arrayListOf<CustomLeague>()
        for(league in leagues){
            val fixtures = league.fixtures?.filter { it.time.status == "LIVE" || it.time.status == "HT" }
                ?.sortedBy { it.time.starting_at.timestamp }
            league.fixtures = fixtures
            if(!fixtures.isNullOrEmpty()){
                returnLeagues.add(league)
            }
        }
        return returnLeagues
    }

    companion object{
        fun newInstance(): LiveMainFragment {
            return LiveMainFragment()
        }
    }



}