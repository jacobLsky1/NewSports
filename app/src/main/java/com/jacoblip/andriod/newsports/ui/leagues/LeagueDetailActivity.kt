package com.jacoblip.andriod.newsports.ui.leagues

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.models.leagues.Coverage
import com.jacoblip.andriod.newsports.data.services.viewmodels.LeaguesViewModel
import com.jacoblip.andriod.newsports.data.services.viewmodels.MainViewModel
import com.jacoblip.andriod.newsports.databinding.ActivityLeagueDetailBinding
import com.jacoblip.andriod.newsports.databinding.ActivityMainBinding
import com.jacoblip.andriod.newsports.ui.leagues.fragments.SelectedLeagueMainFragment
import com.jacoblip.andriod.newsports.utilities.Util
import com.jacoblip.andriod.newsports.utilities.internet.InternetConnectivity
import com.jacoblip.andriod.newsports.utilities.internet.WifiReceiver
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LeagueDetailActivity : AppCompatActivity() {

    private lateinit var wifiReceiver: WifiReceiver
    private val viewModel: LeaguesViewModel by viewModels()
    private lateinit var binding: ActivityLeagueDetailBinding
    var errorDialogIsShowing = false
    var hasStanding :Boolean = false
    var leagueId :Long = 0
    lateinit var coverage:Coverage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //window.navigationBarColor = ContextCompat.getColor(this, R.color.primary)
        }
        binding = ActivityLeagueDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpObservers(this.findViewById(android.R.id.content)/*gets the content view*/)
        setSupportActionBar(binding.toolbar)
       // supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = ""
        wifiReceiver = WifiReceiver()

        hasStanding = intent.getBooleanExtra("live_standings", false)
        val logoPath = intent.getStringExtra("logo_path")
        val countryName = intent.getStringExtra("country_name")
        val leagueName = intent.getStringExtra("league_name")
        leagueId = intent.getLongExtra("season_id", 0L)
        coverage = intent.getSerializableExtra("coverage") as Coverage

        Glide.with(this).load(logoPath).placeholder(R.drawable.goals).into(binding.leagueImage)
        binding.leagueName.text = "$countryName $leagueName"
        setFragment()
    }


    fun setUpObservers(view: View){
        val snackBar: Snackbar =
            Snackbar.make(view, "Can't Connect To Web..", Snackbar.LENGTH_INDEFINITE)
                .setAction("GO TO SETTINGS") {
                    this@LeagueDetailActivity?.let { it1 -> InternetConnectivity.connectToInternet(applicationContext) }
                }
        Util.hasInternet.observe(this, { it ->
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
    }

    private fun setFragment(){
        val fragment = SelectedLeagueMainFragment.newInstance(leagueId, coverage,hasStanding)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.leagues_activity_fragment_container,fragment)
            .commit()
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

    fun makeErrorDialog(num: Int){
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.error_request_dalog, null)
        val checkInternetButton = dialogView.findViewById(R.id.checkInternetButton) as Button
        val yesButton = dialogView.findViewById(R.id.tryAgainButton) as Button

        val alertDialog = AlertDialog.Builder(this@LeagueDetailActivity)
        alertDialog.setView(dialogView).setCancelable(true)

        val dialog = alertDialog.create()
        dialog.show()
        errorDialogIsShowing = true

        yesButton.setOnClickListener {
            dialog.dismiss()
            when(num){
                // TODO: 01/12/2021 depending what error there is try request again
                11->{Util.requestTryAgain.postValue(11)}
                12->{Util.requestTryAgain.postValue(12)}
                13->{Util.requestTryAgain.postValue(13)}
                14->{Util.requestTryAgain.postValue(14)}
            }
            errorDialogIsShowing = false
            Util.requestError.postValue(0)

        }

        checkInternetButton.setOnClickListener {
            this@LeagueDetailActivity.let { InternetConnectivity.connectToInternet(applicationContext) }
        }
    }


}