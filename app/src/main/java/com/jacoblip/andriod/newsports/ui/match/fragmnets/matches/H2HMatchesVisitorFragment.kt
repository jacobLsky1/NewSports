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
import com.jacoblip.andriod.newsports.databinding.H2hMatchesVisitorBinding
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.MatchesLiteAdapter


class H2HMatchesVisitorFragment(var match: Fixture):Fragment() {

    lateinit var binding: H2hMatchesVisitorBinding
    lateinit var viewModel: MatchViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = H2hMatchesVisitorBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root
        view.apply {
            binding.visitorTeamMatchesRV.layoutManager = LinearLayoutManager(context)
            binding.visitorTeamMatchesRV.isNestedScrollingEnabled = true
        }
        viewModel = ViewModelProvider(requireActivity()).get(MatchViewModel::class.java)
        viewModel.fetchVisitorTeamDetailFromAPI(match.visitorteam_id)
        setUpObservers()
        return view
    }

    fun setUpObservers(){
        viewModel.visitorTeamDetails.observe(viewLifecycleOwner,{
            if(it!=null){
                binding.visitorTeamMatchesRV.isVisible = true
                binding.noMatchesToShowTV.isVisible = false
                binding.visitorTeamMatchesRV.adapter = MatchesLiteAdapter(it.localResults.data,it.id,false,requireContext())
            }else{
                binding.visitorTeamMatchesRV.isVisible = false
                binding.noMatchesToShowTV.isVisible = true
            }
        })
    }

    companion object{
        fun newInstance(match: Fixture):H2HMatchesVisitorFragment {
            return H2HMatchesVisitorFragment(match)
        }
    }

}