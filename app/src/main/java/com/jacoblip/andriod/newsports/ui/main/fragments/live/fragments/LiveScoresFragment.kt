package com.jacoblip.andriod.newsports.ui.main.fragments.live.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jacoblip.andriod.newsports.data.models.leagues.CustomLeague
import com.jacoblip.andriod.newsports.data.services.viewmodels.MainViewModel
import com.jacoblip.andriod.newsports.databinding.LiveScoresFragmentBinding
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.main.LiveSoccerMatchesAdapter
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.main.SoccerMatchesAdapter
import com.jacoblip.andriod.newsports.utilities.Util


class LiveScoresFragment():Fragment() {

    lateinit var binding: LiveScoresFragmentBinding
    lateinit var viewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LiveScoresFragmentBinding.inflate(LayoutInflater.from(requireContext()))
        binding.liveMatchesRV.layoutManager = LinearLayoutManager(requireContext())
        val view = binding.root
        view.apply {

        }
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel.loadLiveMatchesFromServer()
        setUpObservers()
        return view
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
        fun newInstance():LiveScoresFragment {
            return LiveScoresFragment()
        }
    }

}