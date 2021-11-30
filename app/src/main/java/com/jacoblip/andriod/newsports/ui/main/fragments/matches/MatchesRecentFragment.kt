package com.jacoblip.andriod.newsports.ui.main.fragments.matches

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.services.viewmodels.MainViewModel
import com.jacoblip.andriod.newsports.databinding.MatchsFragmentMatchesHomeBinding
import com.jacoblip.andriod.newsports.databinding.RecyclerViewLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchesRecentFragment(): Fragment(R.layout.matchs_fragment_recent){



    private lateinit var binding: RecyclerViewLayoutBinding
    lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = RecyclerViewLayoutBinding.inflate(layoutInflater)
        setUpServices()
        setUpObservers()
        setUpFragment()
    }

    private fun setUpObservers(){

    }

    private fun setUpServices(){
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    private fun setUpFragment(){

    }

    companion object{
        fun newInstance(): MatchesRecentFragment {
            return MatchesRecentFragment()
        }
    }
}