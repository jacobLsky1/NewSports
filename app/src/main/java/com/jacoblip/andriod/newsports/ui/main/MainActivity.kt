package com.jacoblip.andriod.newsports.ui.main

import android.app.DatePickerDialog
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.services.viewmodels.MainViewModel
import com.jacoblip.andriod.newsports.databinding.MainActivityMainBinding
import com.jacoblip.andriod.newsports.ui.main.fragments.leagues.LeaguesMainFragment
import com.jacoblip.andriod.newsports.ui.main.fragments.live.LiveMainFragment
import com.jacoblip.andriod.newsports.ui.main.fragments.matches.MatchesMainFragment
import com.jacoblip.andriod.newsports.ui.splash.AboutActivity
import com.jacoblip.andriod.newsports.utilities.SharingUtil
import com.jacoblip.andriod.newsports.utilities.internet.InternetConnectivity
import com.jacoblip.andriod.newsports.utilities.Util
import com.jacoblip.andriod.newsports.utilities.internet.WifiReceiver
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var wifiReceiver: WifiReceiver
    private val viewModel:MainViewModel by viewModels()
    private lateinit var fragment:Fragment
    private lateinit var binding:MainActivityMainBinding
    private lateinit var datePickerDialog: DatePickerDialog
    private var matchesFragment: MatchesMainFragment = MatchesMainFragment.newInstance()
    private var liveFragment: LiveMainFragment = LiveMainFragment.newInstance()
    private var leaguesFragment: LeaguesMainFragment = LeaguesMainFragment.newInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpServices()
        setUpObservers(this.findViewById(android.R.id.content)/*gets the content view*/)
        wifiReceiver = WifiReceiver()
        setUpView()

        val calendar = Calendar.getInstance()
        datePickerDialog = DatePickerDialog(this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    }

    private fun setUpView(){

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_matches -> {
                    binding.toolbarTitle.text = getString(R.string.upcoming_matches_title)
                    setFragment(matchesFragment,R.id.action_matches)
                }
                R.id.action_live -> {
                    binding.toolbarTitle.text = getString(R.string.live_matches_title)
                    setFragment(liveFragment,R.id.action_live)
                }
                R.id.action_leagues -> {
                    binding.toolbarTitle.text = getString(R.string.soccer_league_title)
                    setFragment(leaguesFragment,R.id.action_leagues)
                }
            }
            true
        }

        if(viewModel.currentMainFragment!=null){
            binding.bottomNavigationView.selectedItemId = viewModel.currentMainFragment!!
        }else{
            binding.bottomNavigationView.selectedItemId = R.id.action_matches
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val selectedDate = formatter.format(calendar.time)
       // val intent = Intent(this@MainActivity, DateMatchesActivity::class.java)
        intent.putExtra("date", selectedDate)
        startActivity(intent)
    }

    private fun setUpServices(){

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_about_us) {
            startActivity(Intent(this@MainActivity, AboutActivity::class.java))
        }
        if (id == R.id.action_calendar) {
            datePickerDialog.show()
        }
        if (id == R.id.action_share) {
            SharingUtil.shareApp(this)
        }
        if (id == R.id.action_rate) {
            SharingUtil.openAppInPlaystore(this)
        }
        return super.onOptionsItemSelected(item)
    }

    fun setUpObservers(view: View){
        val snackBar: Snackbar =
            Snackbar.make(view, "Can't Connect To Web..", Snackbar.LENGTH_INDEFINITE)
                .setAction("GO TO SETTINGS") {
                    this@MainActivity?.let { it1 -> InternetConnectivity.connectToInternet(applicationContext) }
                }
        Util.hasInternet.observe(this, Observer { it ->
            if (!it) {
                snackBar.show()
            } else {
                snackBar.dismiss()
            }
        })
    }

    private fun setFragment(fragment:Fragment,num:Int){
        viewModel.currentMainFragment = num
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_activity_fragment_container,fragment)
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
}