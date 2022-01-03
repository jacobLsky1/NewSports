package com.jacoblip.andriod.newsports.ui.main.fragments.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.models.callbacks.MatchesCallback
import com.jacoblip.andriod.newsports.data.services.viewmodels.MainViewModel
import com.jacoblip.andriod.newsports.databinding.MatchesFragmentRecentBinding
import com.jacoblip.andriod.newsports.interfaces.MainRetrofitInstance
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.SoccerMatchesAdapter
import com.jacoblip.andriod.newsports.utilities.BASE_URL
import com.jacoblip.andriod.newsports.utilities.Util

import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class MatchesRecentFragment: Fragment(){



    private lateinit var binding: MatchesFragmentRecentBinding
    lateinit var viewModel: MainViewModel
    var swipeRefreshLayout: SwipeRefreshLayout? = null
    lateinit var matchesRV :RecyclerView
    lateinit var progressBar:ProgressBar
    lateinit var noMatches:TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MatchesFragmentRecentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = binding.swipeRefreshLayout
        matchesRV = binding.matchesRecentRv
        progressBar = binding.progressBar1
        noMatches = binding.noMatchesTV
        matchesRV.layoutManager = LinearLayoutManager(requireContext())
        swipeRefreshLayout!!.setOnRefreshListener {
            matchesRV.isVisible = true
            noMatches.isVisible = false
            swipeRefreshLayout!!.isRefreshing = false;
            val from = requireArguments().getString("from")!!
            val to = requireArguments().getString("to")!!
            viewModel.loadRecentMatchesFromServer(from,to)
        }
        setUpServices()
        setUpObservers()
        setUpFragment()
    }

    private fun setUpObservers(){
        viewModel.listOfRecentMatches.observe(viewLifecycleOwner,{
            if(it!=null){
                matchesRV.isVisible = true
                noMatches.isVisible = false
                matchesRV.adapter = SoccerMatchesAdapter(it,requireContext(),true)
            }else{
                matchesRV.isVisible = false
                noMatches.isVisible = true
            }
        })

        viewModel.isFetchingData.observe(viewLifecycleOwner,{
            binding.progressBar1.isVisible = it
        })
    }

    private fun setUpServices(){
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    private fun setUpFragment(){
        val from = requireArguments().getString("from")!!
        val to = requireArguments().getString("to")!!
        viewModel.loadRecentMatchesFromServer(from,to)
    }



    companion object{
        fun newInstance(): MatchesRecentFragment {
            return MatchesRecentFragment()
        }
    }

}