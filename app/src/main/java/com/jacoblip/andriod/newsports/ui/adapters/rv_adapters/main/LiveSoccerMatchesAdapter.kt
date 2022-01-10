package com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.main

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.bignerdranch.expandablerecyclerview.ChildViewHolder
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter
import com.bignerdranch.expandablerecyclerview.ParentViewHolder
import com.bumptech.glide.Glide
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture
import com.jacoblip.andriod.newsports.data.models.leagues.CustomLeague
import com.jacoblip.andriod.newsports.ui.leagues.LeagueDetailActivity
import com.jacoblip.andriod.newsports.ui.match.MatchActivity
import com.jacoblip.andriod.newsports.ui.team.TeamActivity
import com.jacoblip.andriod.newsports.utilities.Util


class LiveSoccerMatchesAdapter(val matchAdapterBundle: List<CustomLeague>, val context: Context, val hasHappend:Boolean)
    : ExpandableRecyclerAdapter<CustomLeague, Fixture, LiveSoccerMatchesAdapter.LeagueViewHolder, LiveSoccerMatchesAdapter.FixtureViewHolder>(matchAdapterBundle) {

    override fun onCreateParentViewHolder(parentViewGroup: ViewGroup, viewType: Int): LeagueViewHolder {
        return LeagueViewHolder(LayoutInflater.from(parentViewGroup.context!!).inflate(R.layout.item_competition_title, parentViewGroup, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindChildViewHolder(childViewHolder: FixtureViewHolder, parentPosition: Int, childPosition: Int, child: Fixture) {
        childViewHolder.bindData(child)
    }

    override fun onBindParentViewHolder(parentViewHolder: LeagueViewHolder, parentPosition: Int, parent: CustomLeague) {
        parentViewHolder.bindData(parent, parentPosition)
    }

    override fun onCreateChildViewHolder(childViewGroup: ViewGroup, viewType: Int): FixtureViewHolder {
        return FixtureViewHolder(LayoutInflater.from(childViewGroup.context!!).inflate(R.layout.item_soccer_match, childViewGroup, false))
    }


    inner class FixtureViewHolder(itemView: View) : ChildViewHolder<Fixture>(itemView) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bindData(match: Fixture) {

            val homeTeam = match.localTeam.data
            val visitorTeam = match.visitorTeam.data

            itemView.apply {
                var homeTeamIV:ImageView = findViewById(R.id.item_match_home_teamIV)
                var visitorTeamIV:ImageView = findViewById(R.id.item_match_visitor_teamIV)
                var homeTeamTV: TextView = findViewById(R.id.item_match_home_teamTV)
                var visitorTeamTV:TextView = findViewById(R.id.item_match_visitor_teamTV)
                var dateTV :TextView = findViewById(R.id.item_match_dateTV)
                var scoreTV :TextView = findViewById(R.id.item_match_resultTV)

                if(Util.canLoadPhotos=="true") {
                    Glide.with(this).load(homeTeam.logo_path).into(homeTeamIV)
                    Glide.with(this).load(visitorTeam.logo_path).into(visitorTeamIV)
                }
                homeTeamTV.text = homeTeam.name
                visitorTeamTV.text = visitorTeam.name
                var homeScore = match.scores.localteam_score
                var visitorScore = match.scores.visitorteam_score
                scoreTV.text = "$homeScore : $visitorScore"

                if(hasHappend) {
                    dateTV.text = match.time.starting_at.date
                    var homeScore = match.scores.localteam_score
                    var visitorScore = match.scores.visitorteam_score
                    scoreTV.text = "$homeScore : $visitorScore"
                }else{
                    dateTV.text = match.time.starting_at.date
                    var str = match.time.starting_at.time
                    scoreTV.text = str.removeRange((str.length-3),str.length)
                }

                scoreTV.setOnClickListener {
                    val intent = Intent(context, MatchActivity::class.java)
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    // intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    intent.putExtra("match", match)
                    context.startActivity(intent)
                }
                dateTV.setOnClickListener {
                    val intent = Intent(context, MatchActivity::class.java)
                    // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    intent.putExtra("match", match)
                    context.startActivity(intent)
                }

                homeTeamIV.setOnClickListener {
                    val intent = Intent(context, TeamActivity::class.java)
                    // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    // intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    intent.putExtra("team", homeTeam)

                    if(match.colors!=null)
                        intent.putExtra("color", match.colors.localteam.color)

                    context.startActivity(intent)
                }

                visitorTeamIV.setOnClickListener {
                    val intent = Intent(context, TeamActivity::class.java)
                    // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    // intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    intent.putExtra("team", visitorTeam)

                    if(match.colors!=null)
                        intent.putExtra("color", match.colors!!.visitorteam.color)

                    context.startActivity(intent)
                }

            }

        }
    }

    inner class LeagueViewHolder(itemView: View) : ParentViewHolder<CustomLeague, Fixture>(itemView) {
        fun bindData(league: CustomLeague, position: Int) {

            var matchLayout:LinearLayout = itemView.findViewById(R.id.matchLayout)
            var competitionTitle:TextView = itemView.findViewById(R.id.competitionTitle)
            var countryFlag:ImageView = itemView.findViewById(R.id.countryFlag)

            matchLayout.setOnClickListener {
                val intent = Intent(context, LeagueDetailActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.putExtra("season_id", league.current_season_id)
                intent.putExtra("live_standings", league.live_standings)
                intent.putExtra("logo_path", league.logo_path)
                intent.putExtra("country_name", league.country.data.name)
                intent.putExtra("league_name", league.name)
                intent.putExtra("coverage", league.coverage)
                context.startActivity(intent)
            }

            if (league.round != null) {
                val leagueTitle = league.country.data.name + " " + league.name + " • Round " + league.round!!.name.toString()
                competitionTitle.text = leagueTitle.toUpperCase()
            } else {
                competitionTitle.text = (league.country.data.name + " " + league.name).toUpperCase()
            }
            if(Util.canLoadPhotos=="true") {
                Glide.with(context)
                    .load(league.logo_path)
                    .into(countryFlag)
            }

        }
    }

}