package com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.matches

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.models.fixture.lineup.Player

class LineUpAdapter(val players:List<Player>):RecyclerView.Adapter<LineUpAdapter.PlayerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
    when(viewType){

    }
        return PlayerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_lineup_row, parent, false))
    }

    override fun getItemCount():Int {
       return players.size
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {

       val player = players[position]

        holder.itemView.apply {
            var playerName:TextView = findViewById(R.id.playerName)
            var playerNumber:TextView = findViewById(R.id.playerNumber)
            playerName.text = player.position + ". " + player.player_name
            playerNumber.text = ""
        }
    }
    inner class PlayerViewHolder(view: View) :
        RecyclerView.ViewHolder(view)


}