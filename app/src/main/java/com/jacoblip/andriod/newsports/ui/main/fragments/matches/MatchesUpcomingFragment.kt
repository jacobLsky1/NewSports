package com.jacoblip.andriod.newsports.ui.main.fragments.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.models.callbacks.MatchesCallback
import com.jacoblip.andriod.newsports.data.services.viewmodels.MainViewModel
import com.jacoblip.andriod.newsports.databinding.MatchesFragmentUpcomingBinding
import com.jacoblip.andriod.newsports.interfaces.MainRetrofitInstance
import com.jacoblip.andriod.newsports.utilities.Util

import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class MatchesUpcomingFragment: Fragment(){

    private lateinit var binding: MatchesFragmentUpcomingBinding
    lateinit var viewModel: MainViewModel
    var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MatchesFragmentUpcomingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout!!.setOnRefreshListener {
            Toast.makeText(requireContext(), "refreshing", Toast.LENGTH_SHORT).show()
            swipeRefreshLayout!!.isRefreshing = false;
        }
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
        val from = requireArguments().getString("from")!!
        val to = requireArguments().getString("to")!!
        viewModel.loadMatchesFromServer(from,to)
    }



    companion object{
        fun newInstance(): MatchesUpcomingFragment {
            return MatchesUpcomingFragment()
        }
    }


}