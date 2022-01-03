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
import com.jacoblip.andriod.newsports.ui.match.fragmnets.*


class SelectedMatchHomeFragmentsAdapter(fragmentManager: FragmentManager,
                                        val context: Context,
                                        val match: Fixture
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun saveState(): Parcelable? {
        return null
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {

            1 -> {
                SelectedMatchCommentaryFragment.newInstance(match)
            }
            2 -> {
                SelectedMatchMatchesFragment.newInstance(match)
            }
            3 -> {
                SelectedMatchLineUpFragment.newInstance(match)
            }
            4 -> {
               SelectedMatchTimeLineFragment.newInstance(match)
            }
            else -> {
                SelectedMatchStandingsFragment.newInstance(match)
            }
        }
    }

    override fun getCount() = 5

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            1 -> "COMMENTARY"
            2 -> "MATCHES"
            3 -> "LINEUP"
            4 -> "TIMELINE"
            else -> "STANDINGS"
        }
    }

}