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
import com.jacoblip.andriod.newsports.data.services.viewmodels.MainViewModel
import com.jacoblip.andriod.newsports.databinding.LeaguesFragmentHomeBinding
import com.jacoblip.andriod.newsports.databinding.LiveFragmentHomeBinding
import com.jacoblip.andriod.newsports.ui.adapters.pager_adapters.LiveHomeFragmentsAdapter
import com.jacoblip.andriod.newsports.ui.adapters.pager_adapters.MatchesHomeFragmentsAdapter
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

        binding.viewpager.adapter = LiveHomeFragmentsAdapter(childFragmentManager, requireContext())
        binding.tabLayout.setupWithViewPager(binding.viewpager)
        binding.viewpager.isVisible = true
        binding.tabLayout.isVisible = true
        binding.viewpager.currentItem = 0
        binding.viewpager.offscreenPageLimit = 2

    }

    private fun setUpObservers(){

    }


    private fun setUpServices(){
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }


    companion object{
        fun newInstance(): LiveMainFragment {
            return LiveMainFragment()
        }
    }



}