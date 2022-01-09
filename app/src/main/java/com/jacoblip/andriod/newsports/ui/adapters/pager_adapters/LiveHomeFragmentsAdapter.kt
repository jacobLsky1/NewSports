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
import com.jacoblip.andriod.newsports.ui.main.fragments.live.fragments.LiveHighLightsFragment
import com.jacoblip.andriod.newsports.ui.main.fragments.live.fragments.LiveScoresFragment


class LiveHomeFragmentsAdapter(fragmentManager: FragmentManager, val context: Context) : FragmentStatePagerAdapter(fragmentManager) {

    override fun saveState(): Parcelable? {
        return null
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                LiveScoresFragment.newInstance()
            }
            else -> {
                LiveHighLightsFragment.newInstance()            }
        }
    }

    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "LIVE SCORES"
            else -> "HIGHLIGHTS"
        }
    }

}