package com.jacoblip.andriod.newsports.ui.team

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jacoblip.andriod.newsports.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)
    }
}