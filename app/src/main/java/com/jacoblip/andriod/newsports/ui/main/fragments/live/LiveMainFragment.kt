package com.jacoblip.andriod.newsports.ui.main.fragments.live

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.services.viewmodels.MainViewModel
import com.jacoblip.andriod.newsports.databinding.LeaguesFragmentHomeBinding
import com.jacoblip.andriod.newsports.databinding.LiveFragmentHomeBinding
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
            Toast.makeText(requireContext(), "refreshing", Toast.LENGTH_SHORT).show()
            swipeRefreshLayout!!.isRefreshing = false;
        }

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