package com.jacoblip.andriod.newsports.ui.adapters.pager_adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.jacoblip.andriod.newsports.data.models.seasons.topscorers.TopScorer
import com.jacoblip.andriod.newsports.ui.leagues.fragments.topscores.SeasonAssistScorersFragment
import com.jacoblip.andriod.newsports.ui.leagues.fragments.topscores.SeasonCardScorersFragment
import com.jacoblip.andriod.newsports.ui.leagues.fragments.topscores.SeasonGoalScorersFragment

class SeasonTopScorersFragmentsAdapter(
    fragmentManager: FragmentManager,
) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> {
               SeasonGoalScorersFragment()
            }
            1 -> {
                SeasonAssistScorersFragment()
            }
            else -> {
               SeasonCardScorersFragment()
            }
        }
    }

    override fun getCount() = 3

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Goals"
            1 -> "Assists"
            else -> "Cards"
        }
    }

}