package com.jacoblip.andriod.newsports.ui.main.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.services.viewmodels.MainViewModel
import com.jacoblip.andriod.newsports.databinding.MainFragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment:Fragment(R.layout.main_fragment_main) {

    lateinit var viewModel: MainViewModel
    lateinit var binding:MainFragmentMainBinding
    private lateinit var fragment: Fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentMainBinding.inflate(layoutInflater)
        setUpServices()
        setUpObservers()
    }

    private fun setUpServices(){
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    private fun setUpObservers(){
        viewModel.mainFragmentCurrentFragment.observe(viewLifecycleOwner, {
            fragment = it
            setFragment(fragment)
        })
    }

    private fun  setFragment(fragment: Fragment){
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_main_fragment_container,fragment)
            .commit()
    }

    companion object{
        fun newInstance():MainFragment{
            return MainFragment()
        }
    }
}