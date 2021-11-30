package com.jacoblip.andriod.newsports.ui.main

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.services.viewmodels.MainViewModel
import com.jacoblip.andriod.newsports.databinding.MainActivityMainBinding
import com.jacoblip.andriod.newsports.ui.main.fragments.MainFragment
import com.jacoblip.andriod.newsports.utilities.internet.InternetConnectivity
import com.jacoblip.andriod.newsports.utilities.Util
import com.jacoblip.andriod.newsports.utilities.internet.WifiReceiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var wifiReceiver: WifiReceiver
    private lateinit var viewModel:MainViewModel
    private lateinit var fragment:Fragment
    private lateinit var binding:MainActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpServices()
        setUpObservers(this.findViewById(android.R.id.content)/*gets the content view*/)
        wifiReceiver = WifiReceiver()
        setFragment(fragment)
    }

    private fun setUpServices(){
        val viewModel: MainViewModel by viewModels()
        fragment = MainFragment.newInstance()
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

    private fun setFragment(fragment:Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.activity_fragment_container,fragment)
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