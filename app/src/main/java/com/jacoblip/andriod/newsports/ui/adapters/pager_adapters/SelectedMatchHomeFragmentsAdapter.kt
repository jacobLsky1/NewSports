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
import com.jacoblip.andriod.newsports.ui.match.fragmnets.*


class SelectedMatchHomeFragmentsAdapter(fragmentManager: FragmentManager, val context: Context) : FragmentStatePagerAdapter(fragmentManager) {

    override fun saveState(): Parcelable? {
        return null
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {

            1 -> {
                //val bundle = Bundle()
               // bundle.putString("from", DateTimeUtil.getCustomDate(0))
                //bundle.putString("to", DateTimeUtil.getCustomDate(14))
                val fragment =SelectedMatchCommentaryFragment.newInstance()

               // fragment.arguments = bundle
                fragment
            }
            2 -> {
                //val bundle = Bundle()
                // bundle.putString("from", DateTimeUtil.getCustomDate(0))
                //bundle.putString("to", DateTimeUtil.getCustomDate(14))
                val fragment =SelectedMatchMatchesFragment.newInstance()

                // fragment.arguments = bundle
                fragment
            }
            3 -> {
                //val bundle = Bundle()
                // bundle.putString("from", DateTimeUtil.getCustomDate(0))
                //bundle.putString("to", DateTimeUtil.getCustomDate(14))
                val fragment =SelectedMatchLineUpFragment.newInstance()

                // fragment.arguments = bundle
                fragment
            }
            4 -> {
                //val bundle = Bundle()
                // bundle.putString("from", DateTimeUtil.getCustomDate(0))
                //bundle.putString("to", DateTimeUtil.getCustomDate(14))
                val fragment =SelectedMatchTimeLineFragment.newInstance()

                // fragment.arguments = bundle
                fragment
            }
            else -> {
                //val bundle = Bundle()
                // bundle.putString("from", DateTimeUtil.getCustomDate(-14))
                // bundle.putString("to", DateTimeUtil.getCustomDate(-1))
                val fragment = SelectedMatchStandingsFragment.newInstance()
                //fragment.arguments = bundle
                fragment
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