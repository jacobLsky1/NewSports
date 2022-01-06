package com.jacoblip.andriod.newsports.ui.adapters.rv_adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture

class H2HMatchesAdapter(val matches:List<Fixture>):RecyclerView.Adapter<H2HMatchesAdapter.H2hMatchesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H2hMatchesViewHolder {
    when(viewType){

    }

        return H2hMatchesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_h2h_row, parent, false))
    }

    override fun getItemCount():Int {
       return matches.size
    }

    override fun onBindViewHolder(holder: H2hMatchesViewHolder, position: Int) {

       val match = matches[position]

        holder.itemView.apply {
            var teamAName: TextView = findViewById(R.id.teamAName)
            var teamBName: TextView = findViewById(R.id.teamBName)
            var teamAScore: TextView = findViewById(R.id.teamAScore)
            var teamBScore: TextView = findViewById(R.id.teamBScore)


            teamAName.text = match.localTeam.data.name
            teamBName.text = match.visitorTeam.data.name
            when {
                match.scores.localteam_score == match.scores.visitorteam_score -> {

                }
                match.scores.localteam_score < match.scores.visitorteam_score -> {
                    teamBName.setTypeface(null, Typeface.BOLD)
                    teamBScore.setTypeface(null, Typeface.BOLD)
                    teamAScore.setTypeface(null, Typeface.NORMAL)
                    teamAName.setTypeface(null, Typeface.NORMAL)
                }
                else -> {
                    teamAScore.setTypeface(null, Typeface.BOLD)
                    teamAName.setTypeface(null, Typeface.BOLD)
                    teamBScore.setTypeface(null, Typeface.NORMAL)
                    teamBName.setTypeface(null, Typeface.NORMAL)
                }
            }
            teamBScore.text = match.scores.visitorteam_score.toString()
            teamAScore.text = match.scores.localteam_score.toString()
        }
    }

    inner class H2hMatchesViewHolder(view:View):RecyclerView.ViewHolder(view)



}