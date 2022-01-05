package com.jacoblip.andriod.newsports.ui.team.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture
import com.jacoblip.andriod.newsports.data.models.team.Team
import com.jacoblip.andriod.newsports.data.services.viewmodels.TeamViewModel
import com.jacoblip.andriod.newsports.databinding.TeamOverviewFragmentBinding
import com.jacoblip.andriod.newsports.utilities.Util


class TeamOverViewFragment(var team:Team):Fragment() {

    lateinit var binding: TeamOverviewFragmentBinding
    lateinit var viewModel: TeamViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TeamOverviewFragmentBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root
        view.apply {

        }
        viewModel = ViewModelProvider(requireActivity()).get(TeamViewModel::class.java)
        viewModel.getTeamById(team.id)
        viewModel.getTeamMatchesById(team.id)
        setUpObservers()
        return view
    }

    fun setUpObservers(){
        viewModel.team.observe(viewLifecycleOwner,{ team ->
            if(team!=null){
                val venue = team.venue
                if (venue != null) {
                    Glide.with(this).load(venue.data.image_path).into(binding.venueImage)
                    binding.venueName.text = venue.data.name
                }

                val uefaRankingData = team.uefaranking
                if (uefaRankingData != null) {
                    binding.uefaRanking.text = uefaRankingData.data.position.toString()
                } else {
                    binding.uefaRankingLayout.visibility = View.GONE
                }

                val coach = team.coach.data
                Glide.with(this).load(coach.image_path).into(binding.coachImage)
                binding.coachName.text = coach.common_name

                if (team.founded != null && team.founded != 0) {
                    binding.yearLayout.visibility = View.VISIBLE
                    binding.yearFounded.text = team.founded.toString()
                } else {
                    binding.yearLayout.visibility = View.VISIBLE
                }

                if (team.twitter != null) {
                    binding.twitter.visibility = View.VISIBLE
                    binding.twitterHandle.text = team.twitter
                    binding.twitterHandle.setOnClickListener {
                        val twitterName = team.twitter.replace("@", "")
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://twitter.com/$twitterName")
                            )
                        )
                    }
                } else {
                    binding.twitter.visibility = View.GONE
                }

            }


        })

        viewModel.upComingMatch.observe(viewLifecycleOwner,{fixture->
            binding.nextLayout.visibility = View.VISIBLE
            binding.nextMatchLabel.visibility = View.VISIBLE
            binding.nextMatchDate.text = fixture.time.starting_at.date
            binding.nextMatchTime.text = fixture.time.starting_at.time
            binding.nextTeamAName.text = fixture.localTeam.data.name
            binding.nextTeamBName.text = fixture.visitorTeam.data.name
            Glide.with(this).load(fixture.localTeam.data.logo_path).into(binding.nextTeamAImage)
            Glide.with(this).load(fixture.visitorTeam.data.logo_path).into(binding.nextTeamBImage)
        })

        viewModel.preMatch.observe(viewLifecycleOwner,{fixture->
            binding.prevMatchDate.text = fixture.time.starting_at.date
            binding.prevHomeScore.text = fixture.scores.localteam_score.toString()
            binding.prevAwayScore.text = fixture.scores.visitorteam_score.toString()
            binding.prevTeamAName.text = fixture.localTeam.data.name
            binding.prevTeamBName.text = fixture.visitorTeam.data.name
            Glide.with(this).load(fixture.localTeam.data.logo_path).into(binding.prevTeamAImage)
            Glide.with(this).load(fixture.visitorTeam.data.logo_path).into(binding.prevTeamBImage)
        })

        Util.requestTryAgain.observe(viewLifecycleOwner,{
            if(it==3){
                viewModel.getTeamById(team.id)
            }
            if(it==4){
                viewModel.getTeamMatchesById(team.id)
            }
        })
        viewModel.isFetchingData.observe(viewLifecycleOwner,{
            binding.progressbar6.isVisible = it
        })
    }


    companion object{
        fun newInstance(team:Team):TeamOverViewFragment {
            return TeamOverViewFragment(team)
        }
    }

}