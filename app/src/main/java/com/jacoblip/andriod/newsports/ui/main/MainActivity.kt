package com.jacoblip.andriod.newsports.ui.main

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.utilities.InternetConnectivity
import com.jacoblip.andriod.newsports.utilities.Util
import com.jacoblip.andriod.newsports.utilities.WifiReceiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var wifiReceiver: WifiReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_main)
        setUpObservers(this.findViewById(android.R.id.content)/*gets the content view*/)
        wifiReceiver = WifiReceiver()
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