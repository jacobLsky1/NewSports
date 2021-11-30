package com.jacoblip.andriod.newsports.ui.main.fragments.live

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.services.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class LiveMainFragment():Fragment(R.layout.live_fragment_home) {

    lateinit var viewModel: MainViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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