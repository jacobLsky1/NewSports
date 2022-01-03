package com.jacoblip.andriod.newsports.ui.adapters.rv_adapters

import android.content.Context
import android.content.Intent
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bignerdranch.expandablerecyclerview.ChildViewHolder
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter
import com.bignerdranch.expandablerecyclerview.ParentViewHolder
import com.bumptech.glide.Glide
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.models.competitions.Competition
import com.jacoblip.andriod.newsports.data.models.country.Country
import com.jacoblip.andriod.newsports.data.models.standing.Standing
import com.jacoblip.andriod.newsports.data.models.standing.TeamStanding
import com.jacoblip.andriod.newsports.databinding.StandingRowItemBinding
import com.jacoblip.andriod.newsports.databinding.StandingsHeadingRowItemBinding
import com.jacoblip.andriod.newsports.ui.team.TeamActivity
import java.util.*

// FIXME: 03/01/2022 fix the recycler view
class StandingsAdapter(
    private val countries: ArrayList<Standing>,
    private val context: Context,
    private val teamAId: Long,
    private val teamBId: Long
) : ExpandableRecyclerAdapter<Standing, TeamStanding, StandingsAdapter.LeagueStandingHeadingHolder, StandingsAdapter.TeamStandingViewHolder>(
    countries
) {

    lateinit var parentBinding: StandingsHeadingRowItemBinding
    lateinit var childBinding: StandingRowItemBinding

    override fun onBindChildViewHolder(
        childViewHolder: TeamStandingViewHolder,
        parentPosition: Int,
        childPosition: Int,
        child: TeamStanding
    ) {
        childViewHolder.bindData(child)
    }

    override fun onBindParentViewHolder(
        parentViewHolder: LeagueStandingHeadingHolder,
        parentPosition: Int,
        parent: Standing
    ) {
        parentViewHolder.bindData(parent)
    }

    override fun onCreateParentViewHolder(
        parentViewGroup: ViewGroup,
        viewType: Int
    ): LeagueStandingHeadingHolder {
        parentBinding = StandingsHeadingRowItemBinding.inflate(LayoutInflater.from(parentViewGroup.context!!))

        return LeagueStandingHeadingHolder(
            parentBinding.root
        )
    }

    override fun onCreateChildViewHolder(
        childViewGroup: ViewGroup,
        viewType: Int
    ): TeamStandingViewHolder {
        childBinding = StandingRowItemBinding.inflate(LayoutInflater.from(childViewGroup.context!!))
        return TeamStandingViewHolder(
          childBinding.root
        )
    }

    inner class LeagueStandingHeadingHolder(itemView: View) :
        ParentViewHolder<Country, Competition>(itemView) {

        fun bindData(standing: Standing) {
            parentBinding.countryName.text = standing.name
            parentBinding.countryFlag.visibility = View.VISIBLE
        }
    }

    inner class TeamStandingViewHolder(itemView: View) : ChildViewHolder<Competition>(itemView) {

        fun bindData(standing: TeamStanding) {
            childBinding.standingLayout.setOnClickListener {
                val intent = Intent(context, TeamActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.putExtra("team", standing.team.data)
                context.startActivity(intent)
            }
            childBinding.teamName.text = standing.team_name
            if (standing.overall != null) {
                childBinding.matchesPlayed.text = standing.overall.games_played.toString()
                childBinding.matchesWon.text = standing.overall.won.toString()
                childBinding.matchesDraw.text = standing.overall.draw.toString()
                childBinding.matchesLose.text = standing.overall.lost.toString()
            }
            if (standing.total != null) {
                childBinding.goalDiff.text = standing.total.goal_difference
                childBinding.points.text = standing.total.points.toString()
            }
            childBinding.position.text = (standing.position).toString()

            if (standing.result == null) {
                childBinding.position.background =
                    ContextCompat.getDrawable(context, R.drawable.standings_others_position_bg)
                childBinding.position.setTextColor(ContextCompat.getColor(context, R.color.black))
            } else {
                val topPositionStatus = countries[0].standings.data[0].result
                val lastPositionStatus =
                    countries[0].standings.data[countries[0].standings.data.size - 1].result
                if (standing.result == topPositionStatus) {
                    childBinding.position.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.standings_champions_position_bg
                    )
                    childBinding.position.setTextColor(ContextCompat.getColor(context, R.color.white))
                }
                if (standing.result == lastPositionStatus) {
                    childBinding.position.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.standings_relagation_position_bg
                    )
                    childBinding.position.setTextColor(ContextCompat.getColor(context, R.color.white))
                }
                if (standing.result == "UEFA Europa League") {
                    childBinding.position.background =
                        ContextCompat.getDrawable(context, R.drawable.standings_europa_position_bg)
                    childBinding.position.setTextColor(ContextCompat.getColor(context, R.color.white))
                }
            }

            if (standing.team_id == teamAId || standing.team_id == teamBId) {
                childBinding.standingLayout.background =
                    ContextCompat.getDrawable(context, R.drawable.standings_selected_team_bg)
            } else {
                childBinding.standingLayout.background =
                    ContextCompat.getDrawable(context, R.drawable.standings_unselected_team_bg)
            }

            Glide.with(itemView).load(standing.team.data.logo_path).placeholder(R.drawable.goals)
                .into(childBinding.teamFlag)

            val recentForm = standing.recent_form.split("").filter { it.isNotEmpty() }
            val teamForm = SpannableStringBuilder()
            for ((index, form) in recentForm.withIndex()) {
                if (form.isNotEmpty()) {
                    teamForm.append(form).setSpan(getSpanColor(form), index, index + 1, 0)
                }
            }
            childBinding.recentForm.text = teamForm
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
    }
}