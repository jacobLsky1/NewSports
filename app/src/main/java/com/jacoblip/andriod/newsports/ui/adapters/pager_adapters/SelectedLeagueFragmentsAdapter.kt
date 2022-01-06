package com.jacoblip.andriod.newsports.ui.adapters.pager_adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.jacoblip.andriod.newsports.data.models.leagues.Coverage
import com.jacoblip.andriod.newsports.ui.leagues.fragments.matches.SelectedLeagueMatchesFragment
import com.jacoblip.andriod.newsports.ui.leagues.fragments.SelectedLeagueStandingsFragment
import com.jacoblip.andriod.newsports.ui.leagues.fragments.topscores.SelectedLeagueTopScorersFragment

class SelectedLeagueFragmentsAdapter(
        fragmentManager: FragmentManager,
        private val seasonId: Long,
        private val coverage: Coverage,
        val withStandings:Boolean
) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {

        if(withStandings) {
            return when (position) {
                0 -> {
                    val bundle = Bundle()
                    bundle.putLong("season_id", seasonId)
                    bundle.putLong("home_id", 0L)
                    bundle.putLong("away_id", 0L)
                    SelectedLeagueStandingsFragment.newInstance(seasonId,coverage)
                }
                1 -> {
                    SelectedLeagueMatchesFragment.newInstance(seasonId,coverage)
                }
                else -> {
                    SelectedLeagueTopScorersFragment.newInstance(seasonId,coverage)
                }
            }
        }else{
            return when (position) {
                0 -> {
                    SelectedLeagueMatchesFragment.newInstance(seasonId,coverage)
                }
                else -> {
                    SelectedLeagueTopScorersFragment.newInstance(seasonId,coverage)
                }
            }
        }
    }

    override fun getCount():Int {
        return if(withStandings){
            3
        }else{
            2
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if(withStandings){
        return when (position) {
            0 -> "STANDINGS"
            1 -> "MATCHES"
            else -> "TOP SCORERS"
        }
        }else{
            return when (position) {
                0 -> "MATCHES"
                else -> "TOP SCORERS"
            }
        }
    }

}