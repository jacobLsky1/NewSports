package com.jacoblip.andriod.newsports.ui.adapters.rv_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.models.seasons.players.PlayerRanking

class TeamSquadAdapter(private val players: List<PlayerRanking>):RecyclerView.Adapter<TeamSquadAdapter.PlayerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
    when(viewType){

    }

        return PlayerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.squad_row_item, parent, false))
    }

    override fun getItemCount():Int {
       return players.size
    }

    override fun onBindViewHolder(holder:PlayerViewHolder , position: Int) {

       val playerRanking = players[position]

        holder.itemView.apply {

            var playerPicture:ImageView = findViewById(R.id.playerPicture)
            var teamName:TextView = findViewById(R.id.teamName)
            var playerCountry:TextView = findViewById(R.id.playerCountry)
            var positionName:TextView = findViewById(R.id.positionName)

            Glide.with(this).load(playerRanking.player.data.image_path).into(playerPicture)
            teamName.text = playerRanking.player.data.common_name
            playerCountry.text = playerRanking.player.data.birthcountry
            if (playerRanking.position != null) {
                positionName.text = playerRanking.position.data.name
            }
        }
    }

    inner class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}