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
                val bundle = Bundle()
                bundle.putSerializable("fixture", fixture)
                val fragment = H2HmatchesFragment.newInstance()
                fragment.arguments = bundle
                fragment
            }
            1 -> {
                val bundle = Bundle()
                bundle.putLong("team_id", fixture.localteam_id)
                val fragment = H2HMatchesHomeFragment.newInstance()
                fragment.arguments = bundle
                fragment
            }
            else -> {
                val bundle = Bundle()
                bundle.putLong("team_id", fixture.visitorteam_id)
                val fragment = H2HMatchesVisitorFragment.newInstance()
                fragment.arguments = bundle
                fragment
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