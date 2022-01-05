package com.jacoblip.andriod.newsports.ui.match.fragmnets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture
import com.jacoblip.andriod.newsports.data.services.viewmodels.MainViewModel
import com.jacoblip.andriod.newsports.databinding.MatchesFragmentHomeBinding
import com.jacoblip.andriod.newsports.ui.adapters.pager_adapters.MatchesHomeFragmentsAdapter
import com.jacoblip.andriod.newsports.ui.adapters.pager_adapters.SelectedMatchHomeFragmentsAdapter
import com.jacoblip.andriod.newsports.ui.match.MatchActivity
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class SelectedMatchMainFragment(var match:Fixture):Fragment() {

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
        binding.viewpager.adapter = SelectedMatchHomeFragmentsAdapter(childFragmentManager,requireContext(),match)
        binding.tabLayout.setupWithViewPager(binding.viewpager)
        binding.viewpager.isVisible = true
        binding.tabLayout.isVisible = true
        binding.viewpager.offscreenPageLimit = 4
        val scale = resources.displayMetrics.density
        binding.tabLayout.layoutParams.height = (36 * scale + 0.5F).toInt()
        binding.tabLayout.requestLayout()
        binding.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
    }

    private fun setUpObservers(){

    }


    private fun setUpServices(){
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }


    companion object{
        fun newInstance(match:Fixture): SelectedMatchMainFragment {
            return SelectedMatchMainFragment(match)
        }
    }



}