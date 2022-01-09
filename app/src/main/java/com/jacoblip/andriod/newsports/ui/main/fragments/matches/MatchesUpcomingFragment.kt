package com.jacoblip.andriod.newsports.ui.main.fragments.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jacoblip.andriod.newsports.data.services.viewmodels.MainViewModel
import com.jacoblip.andriod.newsports.databinding.MatchesFragmentUpcomingBinding
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.main.SoccerMatchesAdapter
import com.jacoblip.andriod.newsports.utilities.Util

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchesUpcomingFragment(val from:String,val to:String): Fragment(){

    private lateinit var binding: MatchesFragmentUpcomingBinding
    lateinit var viewModel: MainViewModel
    var swipeRefreshLayout: SwipeRefreshLayout? = null
    lateinit var matchesRV : RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var noMatches: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MatchesFragmentUpcomingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = binding.swipeRefreshLayout
        matchesRV = binding.matchsUpcomingRv
        progressBar = binding.progressBar2
        noMatches = binding.noMatchesTV
        matchesRV.layoutManager = LinearLayoutManager(requireContext())
        swipeRefreshLayout!!.setOnRefreshListener {
            matchesRV.isVisible = true
            noMatches.isVisible = false
            swipeRefreshLayout!!.isRefreshing = false;
            viewModel.loadUpcomingMatchesFromServer(from,to)
        }
        setUpServices()
        setUpObservers()
        setUpFragment()
    }

    private fun setUpObservers(){
        viewModel.listOfUpcomingMatches.observe(viewLifecycleOwner,{
            if(it!=null){
                matchesRV.isVisible = true
                noMatches.isVisible = false
                matchesRV.adapter = SoccerMatchesAdapter(it,requireContext(),false)
            }else{
                matchesRV.isVisible = false
                noMatches.isVisible = true
            }
        })

        viewModel.isFetchingData.observe(viewLifecycleOwner,{
            binding.progressBar2.isVisible = it
        })

        Util.requestTryAgain.observe(viewLifecycleOwner,{
            if(it==2){
                setUpFragment()
            }
        })
    }

    private fun setUpServices(){
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    private fun setUpFragment(){
        viewModel.loadUpcomingMatchesFromServer(from,to)
    }



    companion object{
        fun newInstance(from:String,to:String): MatchesUpcomingFragment {
            return MatchesUpcomingFragment(from,to)
        }
    }


}