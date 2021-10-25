package com.jacoblip.andriod.newsports.ui.main.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.jacoblip.andriod.newsports.R
import dagger.hilt.android.AndroidEntryPoint


class MatchesMainFragment():Fragment(R.layout.matchs_fragment_matches_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View){

    }

}