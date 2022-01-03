package com.jacoblip.andriod.newsports.ui.match.fragmnets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture
import com.jacoblip.andriod.newsports.data.services.viewmodels.MatchViewModel
import com.jacoblip.andriod.newsports.databinding.SelectedMatchComentaryBinding
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.CommantaryAdapter


class SelectedMatchCommentaryFragment(var match:Fixture):Fragment() {

    lateinit var binding: SelectedMatchComentaryBinding
    lateinit var viewModel: MatchViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SelectedMatchComentaryBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root
        view.apply {

        }
        viewModel = ViewModelProvider(requireActivity()).get(MatchViewModel::class.java)
        viewModel.getMatchCommentary(match.id)
        setUpObservers()
        return view
    }

    fun setUpObservers(){
        viewModel.matchCommentary.observe(viewLifecycleOwner,{
            if(it.isNotEmpty()) {
                binding.comentaryRV.isVisible = true
                binding.noLineUp.isVisible = false
                binding.comentaryRV.layoutManager = LinearLayoutManager(context)
                binding.comentaryRV.adapter = CommantaryAdapter(it)
            }else{
                binding.comentaryRV.isVisible = false
                binding.noLineUp.isVisible = true
            }
        })

    }

    companion object{
        fun newInstance(match: Fixture):SelectedMatchCommentaryFragment {
            return SelectedMatchCommentaryFragment(match)
        }
    }

}