package com.jacoblip.andriod.newsports.ui.match.fragmnets

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jacoblip.andriod.newsports.data.models.MatchPreviewItem
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture
import com.jacoblip.andriod.newsports.data.services.viewmodels.MatchViewModel
import com.jacoblip.andriod.newsports.databinding.SelectedMatchTimelineBinding
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.MatchPreviewAdapter


class SelectedMatchTimeLineFragment(var match:Fixture):Fragment() {

    lateinit var binding: SelectedMatchTimelineBinding
    lateinit var viewModel: MatchViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SelectedMatchTimelineBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root
        view.apply {

        }
        viewModel = ViewModelProvider(requireActivity()).get(MatchViewModel::class.java)
        setUpView()
        return view
    }

    fun setUpView(){
        val matchPreviewItems: ArrayList<MatchPreviewItem> = arrayListOf()
        val goals = match.goals!!.data
        val cards = match.cards!!.data
        val substitutions = match.substitutions!!.data

        if ((substitutions.size + goals.size + cards.size) > 0) {
            binding.noTimeLine.isVisible = false
            binding.timeLineRV.isVisible = true

            for (goal in goals) {
                val matchPreviewItem = MatchPreviewItem()
                if (goal.team_id.toLong() == match.localteam_id) {
                    matchPreviewItem.match_team = "home"
                } else {
                    matchPreviewItem.match_team = "away"
                }
                matchPreviewItem.type = "goal"
                matchPreviewItem.time = goal.minute
                matchPreviewItem.display_time = goal.minute.toString()
                matchPreviewItem.player_name = goal.player_name
                matchPreviewItem.score = goal.result
                matchPreviewItem.player_assist_name = goal.player_assist_name
                matchPreviewItems.add(matchPreviewItem)
            }

            for (card in cards) {
                val matchPreviewItem = MatchPreviewItem()
                if (card.team_id.toLong() == match.localteam_id) {
                    matchPreviewItem.match_team = "home"
                } else {
                    matchPreviewItem.match_team = "away"
                }
                matchPreviewItem.type = "card"
                matchPreviewItem.time = card.minute
                matchPreviewItem.display_time = card.minute.toString()
                matchPreviewItem.card = card.type
                matchPreviewItem.card_player = card.player_name
                matchPreviewItems.add(matchPreviewItem)
            }

            for (homeSub in substitutions) {
                val matchPreviewItem = MatchPreviewItem()
                if (homeSub.team_id.toLong() == match.localteam_id) {
                    matchPreviewItem.match_team = "home"
                } else {
                    matchPreviewItem.match_team = "away"
                }
                matchPreviewItem.type = "substitution"
                matchPreviewItem.time = homeSub.minute
                matchPreviewItem.display_time = homeSub.minute.toString()
                matchPreviewItem.player_in = homeSub.player_in_name
                matchPreviewItem.player_out = homeSub.player_out_name
                matchPreviewItems.add(matchPreviewItem)
            }

            matchPreviewItems.sortByDescending { it.time }

            val awayAdapter = MatchPreviewAdapter(matchPreviewItems)
            binding.timeLineRV.layoutManager = LinearLayoutManager(context)
            binding.timeLineRV.adapter = awayAdapter
            binding.timeLineRV.isNestedScrollingEnabled = true
        }else{
            binding.noTimeLine.isVisible = true
            binding.timeLineRV.isVisible = false
        }
    }

    companion object{
        fun newInstance(match: Fixture):SelectedMatchTimeLineFragment {
            return SelectedMatchTimeLineFragment(match)
        }
    }

}