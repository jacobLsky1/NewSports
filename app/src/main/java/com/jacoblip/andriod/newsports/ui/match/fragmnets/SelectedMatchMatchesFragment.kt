package com.jacoblip.andriod.newsports.ui.match.fragmnets

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture
import com.jacoblip.andriod.newsports.data.services.viewmodels.MatchViewModel
import com.jacoblip.andriod.newsports.databinding.SelectedMatchMatchesBinding
import com.jacoblip.andriod.newsports.ui.adapters.pager_adapters.H2HFragmentsAdapter
import com.jacoblip.andriod.newsports.ui.match.MatchActivity


class SelectedMatchMatchesFragment(var match:Fixture):Fragment() {

    lateinit var binding: SelectedMatchMatchesBinding
    lateinit var viewModel: MatchViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SelectedMatchMatchesBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root
        view.apply {

        }
        viewModel = ViewModelProvider(requireActivity()).get(MatchViewModel::class.java)
        setUpObservers()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val match = (context as MatchActivity).getMatchObject()
        if (binding.viewpager != null) {
            binding.viewpager.adapter = H2HFragmentsAdapter(childFragmentManager, match!!)
            binding.tabLayout.setupWithViewPager(binding.viewpager)
            binding.viewpager.offscreenPageLimit = 2
            //binding.viewpagerTabsLayout.visibility = View.VISIBLE
            val scale = requireContext().resources.displayMetrics.density
            binding.tabLayout.layoutParams.height = (24 * scale + 0.5F).toInt()
            binding.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
            binding.tabLayout.requestLayout()
            //binding.tabLayout.background = ContextCompat.getDrawable(requireContext(), R.drawable.standings_unselected_team_bg)
        }
    }

    fun setUpObservers(){

    }

    companion object{
        fun newInstance(match: Fixture):SelectedMatchMatchesFragment {
            return SelectedMatchMatchesFragment(match)
        }
    }

}