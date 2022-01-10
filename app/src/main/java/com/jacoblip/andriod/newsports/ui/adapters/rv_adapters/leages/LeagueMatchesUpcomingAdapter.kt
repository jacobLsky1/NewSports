package com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.leages

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.bignerdranch.expandablerecyclerview.ChildViewHolder
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter
import com.bignerdranch.expandablerecyclerview.ParentViewHolder
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture
import com.jacoblip.andriod.newsports.data.models.leagues.LeagueRoundHeader
import com.jacoblip.andriod.newsports.ui.match.MatchActivity
import com.jacoblip.andriod.newsports.utilities.Util


class LeagueMatchesUpcomingAdapter(val leagueHeaders: List<LeagueRoundHeader>, val context: Context, shouldOpenTeamDetails:Boolean)
    : ExpandableRecyclerAdapter<LeagueRoundHeader, Fixture, LeagueMatchesUpcomingAdapter.LeagueViewHolder, LeagueMatchesUpcomingAdapter.FixtureViewHolder>(leagueHeaders) {

    override fun onCreateParentViewHolder(parentViewGroup: ViewGroup, viewType: Int): LeagueViewHolder {
        return LeagueViewHolder(LayoutInflater.from(parentViewGroup.context!!).inflate(R.layout.item_league_round_row, parentViewGroup, false))
    }

    override fun onBindChildViewHolder(childViewHolder: FixtureViewHolder, parentPosition: Int, childPosition: Int, child: Fixture) {
        childViewHolder.bindData(child)
    }

    override fun onBindParentViewHolder(parentViewHolder: LeagueViewHolder, parentPosition: Int, parent: LeagueRoundHeader) {
        parentViewHolder.bindData(parent, parentPosition)
    }

    override fun onCreateChildViewHolder(childViewGroup: ViewGroup, viewType: Int): FixtureViewHolder {
        return FixtureViewHolder(LayoutInflater.from(childViewGroup.context!!).inflate(R.layout.item_fixture_lite_row, childViewGroup, false))
    }

    inner class FixtureViewHolder(itemView: View) : ChildViewHolder<Fixture>(itemView) {
        fun bindData(fixture: Fixture) {

            var fixtureLayout = itemView.findViewById<LinearLayout>(R.id.fixtureLayout)
            var teamAName: TextView = itemView.findViewById(R.id.teamAName)
            var teamBName: TextView = itemView.findViewById(R.id.teamBName)
            var teamAScore: TextView = itemView.findViewById(R.id.teamAScore)
            var teamBScore: TextView = itemView.findViewById(R.id.teamBScore)
            var status:TextView = itemView.findViewById(R.id.status)
            var date:TextView = itemView.findViewById(R.id.date)

            fixtureLayout.setOnClickListener {
                val intent = Intent(context, MatchActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.putExtra("match", fixture)
                context.startActivity(intent)
            }


            teamAName.text = fixture.localTeam.data.name
            teamBName.text = fixture.visitorTeam.data.name
            val boldTypeface = ResourcesCompat.getFont(context, R.font.font_bold)
            val regularTypeface = ResourcesCompat.getFont(context, R.font.font_medium)
            when {
                fixture.scores.localteam_score == fixture.scores.visitorteam_score -> {
                    status.background = ContextCompat.getDrawable(context, R.drawable.match_team_draw_bg)
                    status.text = "D"
                }
                fixture.scores.localteam_score < fixture.scores.visitorteam_score -> {
                    teamBName.typeface = boldTypeface
                    teamBScore.typeface = boldTypeface
                    teamAScore.typeface = regularTypeface
                    teamAName.typeface = regularTypeface
                }
                else -> {
                    teamAScore.typeface = boldTypeface
                    teamAName.typeface = boldTypeface
                    teamBScore.typeface = regularTypeface
                    teamBName.typeface = regularTypeface
                }
            }
            if (fixture.time.status == "NS") {
                teamBScore.text = "-"
                teamAScore.text = "-"
            } else {
                teamBScore.text = fixture.scores.visitorteam_score.toString()
                teamAScore.text = fixture.scores.localteam_score.toString()
            }
            date.text = fixture.time.starting_at.date.drop(5)

        }
    }

    inner class LeagueViewHolder(itemView: View) : ParentViewHolder<LeagueRoundHeader, Fixture>(itemView) {
        fun bindData(league: LeagueRoundHeader, position: Int) {
            var competitionTitle :TextView = itemView.findViewById(R.id.competitionTitle)
            competitionTitle.text = "Round " + league.round
           // if (position != 0 && parent.fixtures!!.size > 2) {
           //     loadRandomBannerAd(matchAdapterBundle.context, itemView.bannerAdsLayout)
            //}
        }
    }

}