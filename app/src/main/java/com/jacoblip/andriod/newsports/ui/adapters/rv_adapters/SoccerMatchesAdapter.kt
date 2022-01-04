package com.jacoblip.andriod.newsports.ui.adapters.rv_adapters

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture
import com.jacoblip.andriod.newsports.ui.match.MatchActivity
import com.jacoblip.andriod.newsports.ui.team.TeamActivity
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime

class SoccerMatchesAdapter(val matchs:List<Fixture>,val context:Context,val hasHappend:Boolean):RecyclerView.Adapter<MatchesItemView>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesItemView {
    when(viewType){

    }

        return MatchesItemView(LayoutInflater.from(parent.context).inflate(R.layout.item_soccer_match, parent, false))
    }

    override fun getItemCount():Int {
       return matchs.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MatchesItemView, position: Int) {

       val match = matchs[position]
        val homeTeam = match.localTeam.data
        val visitorTeam = match.visitorTeam.data

        holder.itemView.apply {

            var homeTeamIV:ImageView = findViewById(R.id.item_match_home_teamIV)
            var visitorTeamIV:ImageView = findViewById(R.id.item_match_visitor_teamIV)
            var homeTeamTV: TextView = findViewById(R.id.item_match_home_teamTV)
            var visitorTeamTV:TextView = findViewById(R.id.item_match_visitor_teamTV)
            var dateTV :TextView = findViewById(R.id.item_match_dateTV)
            var scoreTV :TextView = findViewById(R.id.item_match_resultTV)

            Glide.with(this).load(homeTeam.logo_path).into(homeTeamIV)
            Glide.with(this).load(visitorTeam.logo_path).into(visitorTeamIV)
            homeTeamTV.text = homeTeam.name
            visitorTeamTV.text = visitorTeam.name
            var homeScore = match.scores.localteam_score
            var visitorScore = match.scores.visitorteam_score
            scoreTV.text = "$homeScore : $visitorScore"

            var today = LocalDate.now()
            var matchDate = LocalDate.parse(match.time.starting_at.date)
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
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.putExtra("match", match)
                context.startActivity(intent)
            }
            dateTV.setOnClickListener {
                val intent = Intent(context, MatchActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.putExtra("match", match)
                context.startActivity(intent)
            }

            homeTeamIV.setOnClickListener {
                val intent = Intent(context, TeamActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.putExtra("team", homeTeam)

                if(match.colors!=null)
                intent.putExtra("color", match.colors.localteam.color)

                context.startActivity(intent)
            }

            visitorTeamIV.setOnClickListener {
                val intent = Intent(context, TeamActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.putExtra("team", visitorTeam)

                if(match.colors!=null)
                intent.putExtra("color", match.colors!!.visitorteam.color)

                context.startActivity(intent)
            }
        }
    }



}