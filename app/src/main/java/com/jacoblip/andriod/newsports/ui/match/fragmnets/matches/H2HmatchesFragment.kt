package com.jacoblip.andriod.newsports.ui.match.fragmnets.matches

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
import com.jacoblip.andriod.newsports.databinding.H2hMatchesBinding
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.H2HMatchesAdapter


class H2HmatchesFragment(var match: Fixture):Fragment() {

    lateinit var binding: H2hMatchesBinding
    lateinit var viewModel: MatchViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = H2hMatchesBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root
        view.apply {
            binding.H2Hrv.layoutManager = LinearLayoutManager(context)
            binding.H2Hrv.isNestedScrollingEnabled = true
        }
        viewModel = ViewModelProvider(requireActivity()).get(MatchViewModel::class.java)
        viewModel.getH2Hmatches(match)
        setUpObservers()
        return view
    }

    fun setUpObservers(){
       viewModel.listOfH2HMatches.observe(viewLifecycleOwner,{
           if(!it.isNullOrEmpty()){
               binding.H2Hrv.isVisible = true
               binding.noMatchesToShowTV.isVisible = false
               binding.H2Hrv.adapter = H2HMatchesAdapter(it)
           }else{
               binding.H2Hrv.isVisible = false
               binding.noMatchesToShowTV.isVisible = true
           }
       })
    }

    companion object{
        fun newInstance(match: Fixture):H2HmatchesFragment {
            return H2HmatchesFragment(match)
        }
    }
    
}