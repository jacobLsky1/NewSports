package com.jacoblip.andriod.newsports.ui.adapters.rv_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.models.commentaries.Commentary

class CommantaryAdapter(val comments:List<Commentary>):RecyclerView.Adapter<CommantaryAdapter.LiveGameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveGameViewHolder {
        when (viewType) {

        }
        return LiveGameViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.item_commentary_row, parent, false))
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: LiveGameViewHolder, position: Int) {

        val comment = comments[position]

        holder.itemView.apply {

            var commentary: TextView = findViewById(R.id.commentary)
            var commentaryTime: TextView = findViewById(R.id.commentaryTime)
            var commentaryIcon: ImageView = findViewById(R.id.commentaryIcon)
            commentary.text = comment.comment
            commentaryTime.text = comment.minute.toString() + "\""
            if (comment.goal || comment.important) {
                commentary.typeface = ResourcesCompat.getFont(context, R.font.font_bold)
                commentaryIcon.visibility = View.VISIBLE
            } else {
                commentary.typeface = ResourcesCompat.getFont(context, R.font.font_regular)
                commentaryIcon.visibility = View.GONE
            }
        }
    }

    inner class LiveGameViewHolder(view: View) :
        RecyclerView.ViewHolder(view)
}
