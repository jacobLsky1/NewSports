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




class MatchesHomeFragmentsAdapter(fragmentManager: FragmentManager, val context: Context) : FragmentStatePagerAdapter(fragmentManager) {

    override fun saveState(): Parcelable? {
        return null
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                val bundle = Bundle()
                bundle.putString("from", DateTimeUtil.getCustomDate(-14))
                bundle.putString("to", DateTimeUtil.getCustomDate(-1))
                val fragment = MatchesRecentFragment.newInstance()
                fragment.arguments = bundle
                fragment
            }
            else -> {
                val bundle = Bundle()
                bundle.putString("from", DateTimeUtil.getCustomDate(0))
                bundle.putString("to", DateTimeUtil.getCustomDate(14))
                val fragment =MatchesUpcomingFragment.newInstance()

                fragment.arguments = bundle
                fragment
            }
        }
    }

    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Recent"
            else -> "Upcoming"
        }
    }

}