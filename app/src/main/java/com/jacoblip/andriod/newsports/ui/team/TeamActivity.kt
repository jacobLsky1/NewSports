package com.jacoblip.andriod.newsports.ui.team

import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.models.fixture.color.TeamColor
import com.jacoblip.andriod.newsports.data.models.team.Team
import com.jacoblip.andriod.newsports.data.services.viewmodels.TeamViewModel
import com.jacoblip.andriod.newsports.databinding.ActivityTeamDetailBinding
import com.jacoblip.andriod.newsports.ui.match.fragmnets.SelectedMatchMainFragment
import com.jacoblip.andriod.newsports.ui.team.fragments.TeamMainFragment
import com.jacoblip.andriod.newsports.utilities.Util
import com.jacoblip.andriod.newsports.utilities.internet.InternetConnectivity
import com.jacoblip.andriod.newsports.utilities.internet.WifiReceiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamActivity : AppCompatActivity() {

    private lateinit var wifiReceiver: WifiReceiver
    private val viewModel: TeamViewModel by viewModels()
    private lateinit var binding: ActivityTeamDetailBinding
    private lateinit var team: Team
    var errorDialogIsShowing = false
    var color ="#000000"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.primary)
        }
        setSupportActionBar(binding.toolbar)
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = ""
        team = intent.getSerializableExtra("team") as Team
        color = intent.getStringExtra("color").toString()
        if(color=="null"||color==""){
            color ="#000000"
        }
        setUpServices()
        setUpView()
        setUpObservers(this.findViewById(android.R.id.content)/*gets the content view*/)

    }

    private fun setUpView(){


            if (color != null) {
                Log.d("BugTracer", "team color is not null: " + color)
                binding.appBarLayout.setBackgroundColor(Color.parseColor(color))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val window = window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = Color.parseColor(color)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.navigationBarColor = Color.parseColor(color)
                    }
                }
                if (ColorUtils.calculateLuminance(Color.parseColor(color)) < 0.5) {
                    //this is dark color
                    binding.leagueName.setTextColor(resources.getColor(R.color.white))
                } else {
                    //this is light color
                    binding.leagueName.setTextColor(resources.getColor(R.color.black))
                }
            }else{
                Log.d("BugTracer", "team color is null: 1")
            }


        if(Util.canLoadPhotos=="true") {
            Glide.with(this).load(team.logo_path).placeholder(R.drawable.goals)
                .into(binding.leagueImage)
        }
        binding.leagueName.text = team.name


        setFragment()
    }

    private fun setFragment(){
        val fragment = TeamMainFragment.newInstance(team)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.team_activity_fragment_container,fragment)
            .commit()
    }


    private fun setUpServices(){
        wifiReceiver = WifiReceiver()
    }

    private fun setUpObservers(view: View){
        val snackBar: Snackbar =
            Snackbar.make(view, "Can't Connect To Web..", Snackbar.LENGTH_INDEFINITE)
                .setAction("GO TO SETTINGS") {
                    this@TeamActivity?.let { it1 -> InternetConnectivity.connectToInternet(applicationContext) }
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

        viewModel.team.observe(this,{
            if(it!=null){
                binding.countryName.text = it.country.data.name
            }
        })
    }

    fun makeErrorDialog(num: Int) {
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.error_request_dalog, null)
        val checkInternetButton = dialogView.findViewById(R.id.checkInternetButton) as Button
        val yesButton = dialogView.findViewById(R.id.tryAgainButton) as Button

        val alertDialog = AlertDialog.Builder(this@TeamActivity)
        alertDialog.setView(dialogView).setCancelable(true)

        val dialog = alertDialog.create()
        dialog.show()
        errorDialogIsShowing = true

        yesButton.setOnClickListener {
            dialog.dismiss()
            when (num) {
                3->{Util.requestTryAgain.postValue(3)}
                4->{Util.requestTryAgain.postValue(4)}
                5->{Util.requestTryAgain.postValue(5)}
                6->{Util.requestTryAgain.postValue(6)}
                7->{Util.requestTryAgain.postValue(7)}
            }
            errorDialogIsShowing = false
            Util.requestError.postValue(0)

        }

        checkInternetButton.setOnClickListener {
            this@TeamActivity.let { InternetConnectivity.connectToInternet(applicationContext) }
        }
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
}