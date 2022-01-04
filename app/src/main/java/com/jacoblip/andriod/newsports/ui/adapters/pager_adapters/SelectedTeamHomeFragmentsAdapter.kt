package com.jacoblip.andriod.newsports.ui.adapters.pager_adapters

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.jacoblip.andriod.newsports.ui.main.fragments.matches.MatchesRecentFragment
import com.jacoblip.andriod.newsports.ui.main.fragments.matches.MatchesUpcomingFragment

import com.jacoblip.andriod.newsports.utilities.DateTimeUtil
import android.os.Parcelable
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture
import com.jacoblip.andriod.newsports.data.models.team.Team
import com.jacoblip.andriod.newsports.ui.match.fragmnets.*
import com.jacoblip.andriod.newsports.ui.team.fragments.TeamMatchesFragment
import com.jacoblip.andriod.newsports.ui.team.fragments.TeamOverViewFragment
import com.jacoblip.andriod.newsports.ui.team.fragments.TeamResultsFragment
import com.jacoblip.andriod.newsports.ui.team.fragments.TeamSquadFragment


class SelectedTeamHomeFragmentsAdapter(fragmentManager: FragmentManager,
                                       val context: Context,
                                       val team: Team
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun saveState(): Parcelable? {
        return null
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {

            1 -> {
                TeamResultsFragment.newInstance(team)
            }
            2 -> {
                TeamMatchesFragment.newInstance(team)
            }
            3 -> {
                TeamSquadFragment.newInstance(team)
            }
            else -> {
                TeamOverViewFragment.newInstance(team)
            }
        }
    }

    override fun getCount() = 4

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            1 -> "RESULTS"
            2 -> "MATCHES"
            3 -> "SQUAD"
            else -> "OVERVIEW"
        }
    }

}