package com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.matches

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jacoblip.andriod.newsports.R

import com.jacoblip.andriod.newsports.data.models.MatchPreviewItem

class MatchPreviewAdapter(private val events: List<MatchPreviewItem>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val advertViewType = 1
    private val homeEventViewType = 2
    private val awayEventViewType = 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return when(viewType){
        1 -> HomeEventViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.item_match_preview_home_event, parent, false))
        else -> AwayEventViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.item_match_preview_away_event, parent, false))
    }
    }

    override fun getItemCount():Int {
       return events.size
    }

    override fun onBindViewHolder(holder:RecyclerView.ViewHolder , position: Int) {

        val event = events[position]

        if(holder.itemViewType==1){
            holder.itemView.apply {
                var eventIcon:ImageView = findViewById(R.id.eventIcon)
                var playerName:TextView = findViewById(R.id.playerName)
                var eventTime:TextView = findViewById(R.id.eventTime)

                when (event.type) {
                    "goal" -> {
                        eventIcon.setImageResource(R.drawable.ico_football_regular_goal)
                        playerName.text = event.player_name
                    }
                    "card" -> {
                        if (event.card == "yellowcard") {
                            eventIcon.setImageResource(R.drawable.match_yellow_card)
                        } else {
                            eventIcon.setImageResource(R.drawable.match_red_card)
                        }
                        playerName.text = event.card_player
                    }
                    else -> {
                        playerName.text = event.player_in + "\n" + event.player_out
                        eventIcon.setImageResource(R.drawable.ico_substitution_in)
                    }
                }
                eventTime.text = event.display_time + "\""
            }
        }else {
            holder.itemView.apply {

                var awayEventIcon:ImageView = findViewById(R.id.awayEventIcon)
                var awayEventName:TextView = findViewById(R.id.awayEventName)
                var awayEventTime:TextView = findViewById(R.id.awayEventTime)

                when (event.type) {
                    "goal" -> {
                        awayEventIcon.setImageResource(R.drawable.ico_football_regular_goal)
                        awayEventName.text = event.player_name
                    }
                    "card" -> {
                        if (event.card == "yellowcard") {
                            awayEventIcon.setImageResource(R.drawable.match_yellow_card)
                        } else {
                            awayEventIcon.setImageResource(R.drawable.match_red_card)
                        }
                        awayEventName.text = event.card_player
                    }
                    else -> {
                        awayEventName.text = event.player_in + "\n" + event.player_out
                        awayEventIcon.setImageResource(R.drawable.ico_substitution_in)
                    }
                }
                awayEventTime.text = event.display_time + "\""
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = events[position]
        return if(item.match_team== "home")
            1
        else
            0
    }

    inner class HomeEventViewHolder(view: View):RecyclerView.ViewHolder(view)
    inner class AwayEventViewHolder(view: View):RecyclerView.ViewHolder(view)

}
