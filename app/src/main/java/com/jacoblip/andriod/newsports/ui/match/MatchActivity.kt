package com.jacoblip.andriod.newsports.ui.match

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture
import com.jacoblip.andriod.newsports.data.services.viewmodels.MatchViewModel
import com.jacoblip.andriod.newsports.databinding.ActivityMatchBinding
import com.jacoblip.andriod.newsports.ui.match.fragmnets.SelectedMatchMainFragment
import com.jacoblip.andriod.newsports.utilities.Util
import com.jacoblip.andriod.newsports.utilities.internet.InternetConnectivity
import com.jacoblip.andriod.newsports.utilities.internet.WifiReceiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchActivity : AppCompatActivity() {

    private lateinit var wifiReceiver: WifiReceiver
    private val viewModel: MatchViewModel by viewModels()
    private lateinit var binding: ActivityMatchBinding
    private lateinit var match:Fixture
    var errorDialogIsShowing = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        match = intent.getSerializableExtra("match") as Fixture
        setUpServices()
        setUpObservers(this.findViewById(android.R.id.content)/*gets the content view*/)
        wifiReceiver = WifiReceiver()
    }

    private fun setUpView(){
        setSupportActionBar(binding.toolbar)
        binding.toolbarTitle.text = "${match.league?.data?.country?.data?.name}::${match.league?.data?.name}"
        val homeTeam = match.localTeam.data
        val visitorTeam = match.visitorTeam.data
        var homeTeamIV: ImageView = findViewById(R.id.item_match_home_teamIV)
        var visitorTeamIV: ImageView = findViewById(R.id.item_match_visitor_teamIV)
        var homeTeamTV: TextView = findViewById(R.id.item_match_home_teamTV)
        var visitorTeamTV: TextView = findViewById(R.id.item_match_visitor_teamTV)
        var dateTV : TextView = findViewById(R.id.item_match_dateTV)
        var scoreTV : TextView = findViewById(R.id.item_match_resultTV)

        Glide.with(this).load(homeTeam.logo_path).into(homeTeamIV)
        Glide.with(this).load(visitorTeam.logo_path).into(visitorTeamIV)
        homeTeamTV.text = homeTeam.name
        visitorTeamTV.text = visitorTeam.name
        dateTV.text = match.time.starting_at.date
        var homeScore = match.scores.localteam_score
        var visitorScore = match.scores.visitorteam_score
        scoreTV.text = "$homeScore : $visitorScore"
        setFragment()
    }

    private fun setFragment(){
        val fragment = SelectedMatchMainFragment.newInstance(match)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.match_activity_fragment_container,fragment)
            .commit()
    }

    private fun setUpObservers(view: View){
        val snackBar: Snackbar =
            Snackbar.make(view, "Can't Connect To Web..", Snackbar.LENGTH_INDEFINITE)
                .setAction("GO TO SETTINGS") {
                    this@MatchActivity?.let { it1 -> InternetConnectivity.connectToInternet(applicationContext) }
                }
        Util.hasInternet.observe(this,{ it ->
            if (!it) {
                snackBar.show()
            } else {
                snackBar.dismiss()
            }
        })

        Util.requestError.observe(this,{
            if(it!=0){
                if(!errorDialogIsShowing)
                    makeErrorDialog(it)
            }
        })

        viewModel.match.observe(this,{
            if(it!=null){
                match = it
                setUpView()
            }
        })
    }

    fun getMatchObject() = match

    private fun setUpServices(){
        viewModel.getMatchInQuestion(match.id)
    }


    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(wifiReceiver, filter)
    }
    override fun onStop() {
        super.onStop()
        unregisterReceiver(wifiReceiver)
    }

    fun makeErrorDialog(num: Int) {
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.error_request_dalog, null)
        val checkInternetButton = dialogView.findViewById(R.id.checkInternetButton) as Button
        val yesButton = dialogView.findViewById(R.id.tryAgainButton) as Button

        val alertDialog = AlertDialog.Builder(this@MatchActivity)
        alertDialog.setView(dialogView).setCancelable(true)

        val dialog = alertDialog.create()
        dialog.show()
        errorDialogIsShowing = true

        yesButton.setOnClickListener {
            dialog.dismiss()
            when (num) {
                // TODO: 01/12/2021 depending what error there is try request again
                1 -> {
                }
                2 -> {
                }
                3 -> {
                }
                4 -> {
                }
                5 -> {
                }
            }
            errorDialogIsShowing = false
            Util.requestError.postValue(0)

        }

        checkInternetButton.setOnClickListener {
            this@MatchActivity.let { InternetConnectivity.connectToInternet(applicationContext) }
        }
    }
}