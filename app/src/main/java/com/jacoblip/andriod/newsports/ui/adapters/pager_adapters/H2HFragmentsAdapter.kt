package com.jacoblip.andriod.newsports.ui.adapters.pager_adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture
import com.jacoblip.andriod.newsports.ui.match.fragmnets.matches.H2HMatchesHomeFragment
import com.jacoblip.andriod.newsports.ui.match.fragmnets.matches.H2HMatchesVisitorFragment
import com.jacoblip.andriod.newsports.ui.match.fragmnets.matches.H2HmatchesFragment

class H2HFragmentsAdapter(
        fragmentManager: FragmentManager,
        private val fixture: Fixture
) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                H2HmatchesFragment.newInstance(fixture)
            }
            1 -> {
                H2HMatchesHomeFragment.newInstance(fixture)
            }
            else -> {
                H2HMatchesVisitorFragment.newInstance(fixture)
            }
        }
    }

    override fun getCount() = 3

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "H2H"
            1 -> fixture.localTeam.data.name + " Results"
            else -> fixture.visitorTeam.data.name + " Results"
        }
    }

}