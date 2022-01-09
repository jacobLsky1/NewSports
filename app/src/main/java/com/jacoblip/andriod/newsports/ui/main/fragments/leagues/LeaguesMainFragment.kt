package com.jacoblip.andriod.newsports.ui.main.fragments.leagues

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.services.viewmodels.MainViewModel
import com.jacoblip.andriod.newsports.databinding.LeaguesFragmentHomeBinding
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.main.CountriesAdapter
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class LeaguesMainFragment():Fragment() {

    lateinit var viewModel: MainViewModel
    lateinit var binding: LeaguesFragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LeaguesFragmentHomeBinding.inflate(layoutInflater)
        binding.countriesRV.layoutManager = LinearLayoutManager(requireContext())
        setUpServices()
        setUpObservers()
        return binding.root
    }

    private fun setUpObservers() {
        viewModel.listOfAllCountries.observe(viewLifecycleOwner,{
            if(!it.isNullOrEmpty()){
                binding.countriesRV.adapter = CountriesAdapter(it,requireContext())
                binding.progressBar7.isVisible = false
            }
        })
    }


    private fun setUpServices() {
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel.getAllCountriesFromServer()
    }


    companion object {
        fun newInstance(): LeaguesMainFragment {
            return LeaguesMainFragment()
        }
    }

}