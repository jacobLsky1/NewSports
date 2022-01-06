package com.jacoblip.andriod.newsports.ui.adapters.pager_adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.jacoblip.andriod.newsports.ui.leagues.fragments.matches.SeasonResultMatchesFragment
import com.jacoblip.andriod.newsports.ui.leagues.fragments.matches.SeasonUpcomingMatchesFragment

class SeasonMatchesFragmentsAdapter(fragmentManager: FragmentManager,val seasonId:Long) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SeasonResultMatchesFragment.newInstance(seasonId)
            else -> SeasonUpcomingMatchesFragment.newInstance(seasonId)
        }
    }

    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Results"
            else -> "Upcoming"
        }
    }

}