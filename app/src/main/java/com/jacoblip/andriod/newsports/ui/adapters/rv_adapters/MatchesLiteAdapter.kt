package com.jacoblip.andriod.newsports.ui.adapters.rv_adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture
import com.jacoblip.andriod.newsports.ui.match.MatchActivity

class MatchesLiteAdapter(val matches:List<Fixture>, private val currentTeamId:Long,val goToMatch:Boolean,val context: Context):RecyclerView.Adapter<MatchesLiteAdapter.MatchesLiteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesLiteViewHolder {
    when(viewType){

    }

        return MatchesLiteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_fixture_lite_row, parent, false))
    }

    override fun getItemCount():Int {
       return matches.size
    }

    override fun onBindViewHolder(holder: MatchesLiteViewHolder, position: Int) {

       val match = matches[position]

        holder.itemView.apply {

            var status:TextView = findViewById(R.id.status)
            var teamAName: TextView = findViewById(R.id.teamAName)
            var teamBName: TextView = findViewById(R.id.teamBName)
            var teamAScore: TextView = findViewById(R.id.teamAScore)
            var teamBScore: TextView = findViewById(R.id.teamBScore)
            var date: TextView = findViewById(R.id.date)

            if (currentTeamId == 0L) {
                status.visibility = View.GONE
            } else {
                status.visibility = View.VISIBLE
            }

            teamAName.text = match.localTeam.data.name
            teamBName.text = match.visitorTeam.data.name
            val boldTypeface = ResourcesCompat.getFont(context, R.font.font_bold)
            val regularTypeface = ResourcesCompat.getFont(context, R.font.font_medium)
            when {
                match.scores.localteam_score == match.scores.visitorteam_score -> {
                    status.background = ContextCompat.getDrawable(context, R.drawable.match_team_draw_bg)
                    status.text = "D"
                }
                match.scores.localteam_score < match.scores.visitorteam_score -> {
                    teamBName.typeface = boldTypeface
                    teamBScore.typeface = boldTypeface
                    teamAScore.typeface = regularTypeface
                    teamAName.typeface = regularTypeface
                    if (currentTeamId == match.localteam_id) {
                        status.background = ContextCompat.getDrawable(context, R.drawable.match_team_lose_bg)
                        status.text = "L"
                    } else {
                        status.background = ContextCompat.getDrawable(context, R.drawable.match_team_win_bg)
                        status.text = "W"
                    }
                }
                else -> {
                    teamAScore.typeface = boldTypeface
                    teamAName.typeface = boldTypeface
                    teamBScore.typeface = regularTypeface
                    teamBName.typeface = regularTypeface

                    if (currentTeamId == match.visitorteam_id) {
                        status.background = ContextCompat.getDrawable(context, R.drawable.match_team_lose_bg)
                        status.text = "L"
                    } else {
                        status.text = "W"
                        status.background = ContextCompat.getDrawable(context, R.drawable.match_team_win_bg)
                    }
                }
            }
            if (match.time.status == "NS") {
                status.visibility = View.GONE
                teamBScore.text = "-"
                teamAScore.text = "-"
            } else {
                teamBScore.text = match.scores.visitorteam_score.toString()
                status.visibility = View.VISIBLE
                teamAScore.text = match.scores.localteam_score.toString()
            }
            date.text = match.time.starting_at.date.drop(5)
        }

        if(goToMatch){
            holder.itemView.setOnClickListener {
                val intent = Intent(context, MatchActivity::class.java)
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.putExtra("match", match)
                context.startActivity(intent)
            }
        }
    }

inner class MatchesLiteViewHolder(view:View):RecyclerView.ViewHolder(view)

}