package com.jacoblip.andriod.newsports.ui.match.fragmnets.matches

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
import com.jacoblip.andriod.newsports.databinding.H2hMatchsHomeBinding
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.H2HMatchesAdapter
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.MatchesLiteAdapter


class H2HMatchesHomeFragment(var match: Fixture):Fragment() {

    lateinit var binding: H2hMatchsHomeBinding
    lateinit var viewModel: MatchViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = H2hMatchsHomeBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root
        view.apply {
            binding.homeTeamMatchesRV.layoutManager = LinearLayoutManager(context)
            binding.homeTeamMatchesRV.isNestedScrollingEnabled = true
        }
        viewModel = ViewModelProvider(requireActivity()).get(MatchViewModel::class.java)
        viewModel.fetchHomeTeamDetailFromAPI(match.localteam_id)
        setUpObservers()
        return view
    }

    fun setUpObservers(){
        viewModel.homeTeamDetails.observe(viewLifecycleOwner,{
            if(it!=null){
                binding.homeTeamMatchesRV.isVisible = true
                binding.noMatchesToShowTV.isVisible = false
                binding.homeTeamMatchesRV.adapter = MatchesLiteAdapter(it.localResults.data,it.id)
            }else{
                binding.homeTeamMatchesRV.isVisible = false
                binding.noMatchesToShowTV.isVisible = true
            }
        })
    }

    companion object{
        fun newInstance(match: Fixture):H2HMatchesHomeFragment {
            return H2HMatchesHomeFragment(match)
        }
    }

}