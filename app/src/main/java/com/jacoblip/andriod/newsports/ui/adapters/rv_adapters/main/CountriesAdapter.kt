package com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bignerdranch.expandablerecyclerview.ChildViewHolder
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter
import com.bignerdranch.expandablerecyclerview.ParentViewHolder
import com.bumptech.glide.Glide
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.models.competitions.Competition
import com.jacoblip.andriod.newsports.data.models.country.Country
import com.jacoblip.andriod.newsports.ui.leagues.LeagueDetailActivity


class CountriesAdapter(private val countries: List<Country>, private val context: Context)
    : ExpandableRecyclerAdapter<Country, Competition, CountriesAdapter.CountryViewHolder, CountriesAdapter.LeagueViewHolder>(countries) {
    override fun onCreateParentViewHolder(parentViewGroup: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(LayoutInflater.from(parentViewGroup.context!!).inflate(R.layout.item_country_row, parentViewGroup, false))
    }

    override fun onBindChildViewHolder(childViewHolder: LeagueViewHolder, parentPosition: Int, childPosition: Int, child: Competition) {
        childViewHolder.bindData(child, countries[parentPosition])
    }

    override fun onBindParentViewHolder(parentViewHolder: CountryViewHolder, parentPosition: Int, parent: Country) {
        parentViewHolder.bindData(parent)
    }

    override fun onCreateChildViewHolder(childViewGroup: ViewGroup, viewType: Int): LeagueViewHolder {
        return LeagueViewHolder(LayoutInflater.from(childViewGroup.context!!).inflate(R.layout.item_league_row, childViewGroup, false))
    }

    inner class CountryViewHolder(itemView: View) : ParentViewHolder<Country, Competition>(itemView) {

        fun bindData(country: Country) {

            itemView.apply {

                var countryName:TextView = findViewById(R.id.countryName)
                var countryFlag:ImageView = findViewById(R.id.countryFlag)
                var competitionsCount:TextView = findViewById(R.id.competitionsCount)

                Glide.with(this).load(country.image_path).placeholder(R.drawable.ic_shipping_method_world_normal).into(countryFlag)
                countryName.text = country.name
                competitionsCount.text = country.leagues.data.size.toString() + " competitions"
            }

            //itemView.position.text = (position + 1).toString()
            //itemView.totals.text = playerRanking.assists.toString() + " assists"

        }
    }

    inner class LeagueViewHolder(itemView: View) : ChildViewHolder<Competition>(itemView) {

        fun bindData(league: Competition, country: Country) {

            itemView.apply {

                var leagueName:TextView = findViewById(R.id.leagueName)
                var leagueFlag:ImageView = findViewById(R.id.leagueFlag)
                var leagueLayout:LinearLayout = findViewById(R.id.leagueLayout)

                Glide.with(this).load(league.logo_path).placeholder(R.drawable.ic_shipping_method_world_normal).into(leagueFlag)
                leagueName.text = league.name
                leagueLayout.setOnClickListener {
                    val intent = Intent(context, LeagueDetailActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    intent.putExtra("season_id", league.current_season_id)
                    intent.putExtra("live_standings", league.live_standings)
                    intent.putExtra("logo_path", league.logo_path)
                    intent.putExtra("country_name", country.name)
                    intent.putExtra("league_name", league.name)
                    intent.putExtra("coverage", league.coverage)
                    context.startActivity(intent)
                }
            }


        }
    }
}