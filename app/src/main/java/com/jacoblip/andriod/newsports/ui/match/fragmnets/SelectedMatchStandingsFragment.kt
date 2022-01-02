package com.jacoblip.andriod.newsports.ui.match.fragmnets

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jacoblip.andriod.newsports.data.services.viewmodels.MatchViewModel
import com.jacoblip.andriod.newsports.databinding.SelectedMatchStandingsBinding


class SelectedMatchStandingsFragment():Fragment() {

    lateinit var binding: SelectedMatchStandingsBinding
    lateinit var viewModel: MatchViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SelectedMatchStandingsBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root
        view.apply {

        }
        viewModel = ViewModelProvider(requireActivity()).get(MatchViewModel::class.java)
        setUpObservers()
        return view
    }

    fun setUpObservers(){

    }

    companion object{
        fun newInstance():SelectedMatchStandingsFragment {
            return SelectedMatchStandingsFragment()
        }
    }

}