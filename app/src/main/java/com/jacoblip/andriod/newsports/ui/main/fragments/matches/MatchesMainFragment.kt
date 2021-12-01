package com.jacoblip.andriod.newsports.ui.main.fragments.matches

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
import com.jacoblip.andriod.newsports.data.services.viewmodels.MainViewModel
import com.jacoblip.andriod.newsports.databinding.MatchesFragmentHomeBinding
import com.jacoblip.andriod.newsports.ui.adapters.pager_adapters.MatchHomeFragmentsAdapter
import com.jacoblip.andriod.newsports.ui.adapters.pager_adapters.ZoomOutPageTransformer
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MatchesMainFragment():Fragment() {

    lateinit var viewModel: MainViewModel
    private lateinit var binding: MatchesFragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MatchesFragmentHomeBinding.inflate(layoutInflater)
            return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewpager.adapter = MatchHomeFragmentsAdapter(childFragmentManager, requireContext())
        binding.tabLayout.setupWithViewPager(binding.viewpager)
        binding.viewpager.isVisible = true
        binding.tabLayout.isVisible = true
        binding.viewpager.currentItem = 1
        binding.viewpager.offscreenPageLimit = 2
    }

    private fun setUpObservers(){

    }


    private fun setUpServices(){
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }


    companion object{
        fun newInstance(): MatchesMainFragment {
            return MatchesMainFragment()
        }
    }



}