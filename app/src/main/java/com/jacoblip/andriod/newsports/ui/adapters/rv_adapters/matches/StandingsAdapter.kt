package com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.matches

import android.content.Context
import android.content.Intent
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jacoblip.andriod.newsports.R

import com.jacoblip.andriod.newsports.data.models.standing.TeamStanding
import com.jacoblip.andriod.newsports.ui.team.TeamActivity
import java.util.*

class StandingsAdapter(val standing:List<TeamStanding>,
                       var context: Context,
                       private val teamAId: Long,
                       private val teamBId: Long):RecyclerView.Adapter<StandingsAdapter.StandingsAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingsAdapterViewHolder {
    when(viewType){

    }
        return StandingsAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_standing_row, parent, false))
    }

    override fun getItemCount():Int {
       return standing.size
    }

    override fun onBindViewHolder(holder: StandingsAdapterViewHolder, position: Int) {

       val team = standing[position]

        holder.itemView.apply {

            var standingLayout:RelativeLayout = findViewById(R.id.standingLayout)
            var teamName:TextView = findViewById(R.id.teamName)
            var matchesPlayed:TextView = findViewById(R.id.matchesPlayed)
            var matchesDraw:TextView = findViewById(R.id.matchesDraw)
            var matchesWon:TextView = findViewById(R.id.matchesWon)
            var matchesLose:TextView = findViewById(R.id.matchesLose)
            var goalDiff:TextView = findViewById(R.id.goalDiff)
            var points:TextView = findViewById(R.id.points)
            var recentForm:TextView = findViewById(R.id.recentForm)
            var position:TextView = findViewById(R.id.position)
            var teamFlag:ImageView = findViewById(R.id.teamFlag)

            standingLayout.setOnClickListener {
                val intent = Intent(context, TeamActivity::class.java)
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.putExtra("team", team.team.data)
                intent.putExtra("color","null")
                context.startActivity(intent)
            }
            teamName.text = team.team_name
            if (team.overall != null) {
                matchesPlayed.text = team.overall.games_played.toString()
                matchesWon.text = team.overall.won.toString()
                matchesDraw.text = team.overall.draw.toString()
                matchesLose.text = team.overall.lost.toString()
            }
            if (team.total != null) {
                goalDiff.text = team.total.goal_difference
                points.text = team.total.points.toString()
            }
            position.text = (team.position).toString()

            if (team.result == null) {
                position.background =
                    ContextCompat.getDrawable(context, R.drawable.standings_others_position_bg)
                position.setTextColor(ContextCompat.getColor(context, R.color.black))
            } else {
                val topPositionStatus = standing[0].result
                val lastPositionStatus =
                    standing[standing.size - 1].result
                if (team.result == topPositionStatus) {
                    position.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.standings_champions_position_bg
                    )
                    position.setTextColor(ContextCompat.getColor(context, R.color.white))
                }
                if (team.result == lastPositionStatus) {
                    position.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.standings_relagation_position_bg
                    )
                    position.setTextColor(ContextCompat.getColor(context, R.color.white))
                }
                if (team.result == "UEFA Europa League") {
                    position.background =
                        ContextCompat.getDrawable(context, R.drawable.standings_europa_position_bg)
                    position.setTextColor(ContextCompat.getColor(context, R.color.white))
                }
            }

            if (team.team_id == teamAId || team.team_id == teamBId) {
                standingLayout.background =
                    ContextCompat.getDrawable(context, R.drawable.standings_selected_team_bg)
            } else {
                standingLayout.background =
                    ContextCompat.getDrawable(context, R.drawable.standings_unselected_team_bg)
            }

            Glide.with(this).load(team.team.data.logo_path).placeholder(R.drawable.goals)
                .into(teamFlag)

            val teamRecentForm = team.recent_form.split("").filter { it.isNotEmpty() }
            val teamForm = SpannableStringBuilder()
            for ((index, form) in teamRecentForm.withIndex()) {
                if (form.isNotEmpty()) {
                    teamForm.append(form).setSpan(getSpanColor(form), index, index + 1, 0)
                }
            }
            recentForm.text = teamForm
        }
    }

    private fun getSpanColor(form: String): ForegroundColorSpan {
        return when {
            form.toLowerCase(Locale.getDefault()) == "w" -> ForegroundColorSpan(
                ContextCompat.getColor(
                    context,
                    R.color.standings_recent_form_win
                )
            )
            form.toLowerCase(Locale.getDefault()) == "d" -> ForegroundColorSpan(
                ContextCompat.getColor(
                    context,
                    R.color.standings_recent_form_draw
                )
            )
            else -> ForegroundColorSpan(
                ContextCompat.getColor(
                    context,
                    R.color.standings_recent_form_lose
                )
            )
        }
    }

    inner  class StandingsAdapterViewHolder(view: View):RecyclerView.ViewHolder(view)



}