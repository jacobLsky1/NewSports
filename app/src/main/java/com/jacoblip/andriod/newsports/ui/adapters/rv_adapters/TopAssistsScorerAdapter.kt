package com.jacoblip.andriod.newsports.ui.adapters.rv_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.models.seasons.topscorers.AssistScorer
import com.jacoblip.andriod.newsports.data.models.seasons.topscorers.GoalScorer

class TopAssistsScorerAdapter(val players:List<AssistScorer>):RecyclerView.Adapter<TopAssistsScorerAdapter.TopScorerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):TopScorerViewHolder  {
    when(viewType){

    }

        return TopScorerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_top_scorer_row, parent, false))
    }

    override fun getItemCount():Int {
       return players.size
    }

    override fun onBindViewHolder(holder: TopScorerViewHolder, position: Int) {

       val player = players[position]

        holder.itemView.apply {

            var teamName:TextView = findViewById(R.id.teamName)
            var teamFlag:ImageView = findViewById(R.id.teamFlag)
            var playerPic:ImageView = findViewById(R.id.playerPicture)
            var countryName:TextView = findViewById(R.id.countryName)
            var totals:TextView = findViewById(R.id.totals)
            var playerPosition:TextView = findViewById(R.id.position)

            Glide.with(this).load(player.player.data.image_path).into(playerPic)
            Glide.with(this).load(player.team.data.logo_path).into(teamFlag)
            teamName.text = player.player.data.common_name
            countryName.text = player.player.data.nationality
            totals.text = player.assists.toString() + " assists"
            playerPosition.text = (position + 1).toString()
        }
    }

    inner class TopScorerViewHolder(view: View):RecyclerView.ViewHolder(view)

}