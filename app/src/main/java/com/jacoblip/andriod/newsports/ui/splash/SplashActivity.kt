package com.jacoblip.andriod.newsports.ui.splash

import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.models.ApiKey
import com.jacoblip.andriod.newsports.data.services.viewmodels.TermsViewModel
import com.jacoblip.andriod.newsports.databinding.ActivitySplashBinding
import com.jacoblip.andriod.newsports.interfaces.TermsRetrofitInstance
import com.jacoblip.andriod.newsports.ui.main.MainActivity
import com.jacoblip.andriod.newsports.utilities.Util
import com.jacoblip.andriod.newsports.utilities.internet.InternetConnectivity
import com.jacoblip.andriod.newsports.utilities.internet.WifiReceiver
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class SplashActivity: AppCompatActivity() {

    private lateinit var wifiReceiver: WifiReceiver
    lateinit var binding : ActivitySplashBinding
    val viewModel: TermsViewModel by viewModels()
    var gotTerms = false
    var firstInit = true
    var activityFinished = false
    private var API_KEY = ""
    lateinit var prefs: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        prefs = getSharedPreferences(resources.getString(R.string.app_name), MODE_PRIVATE)
        firstInit = prefs.getBoolean("firstInit", true)
        wifiReceiver = WifiReceiver()
        setUpObservers(view)
    }





    private fun setUpObservers(view: View){
        val snackBar: Snackbar =
            Snackbar.make(view, "Can't Connect To Web..", Snackbar.LENGTH_INDEFINITE)
                .setAction("GO TO SETTINGS") {
                    this@SplashActivity.let { InternetConnectivity.connectToInternet(applicationContext) }
                }


        viewModel.termsChanged.observe(this,{
            val change = it
            if(change&&!this.isDestroyed){
                prefs.edit().putBoolean("acceptedTerms", false).apply()
                checkCanMoveOn()
            }else{
                if(!activityFinished){
                    gotTerms = true
                    checkCanMoveOn()
                }
            }
        })

        Util.hasInternet.observe(this,{ it ->
            if (!it) {
                snackBar.show()
            } else {
                if(!activityFinished) {
                    val acceptedTerms = prefs.getBoolean("acceptedTerms", false)
                    if (!acceptedTerms) {
                        Handler().postDelayed({
                            if(firstInit) {
                                apidata()
                            }else{
                                viewModel.getTermsChange()
                            }
                        }, 1000)
                    } else {
                        if(firstInit){
                            apidata()
                        }else {
                            Handler().postDelayed({ checkCanMoveOn() }, 1000)
                        }
                    }
                    snackBar.dismiss()
                }
            }
        })

        Util.problem.observe(this,{

            val snackBar: Snackbar =
                Snackbar.make(view, "A Problem Has Occurred...", Snackbar.LENGTH_INDEFINITE)
                    .setAction("TRY AGAIN") {
                        this@SplashActivity.let {
                            apidata()
                            snackBar.dismiss()
                        }
                    }
            if(it){
                snackBar.show()
                Util.problem.postValue(false)
            }
        })
    }

    private fun checkCanMoveOn() {

        val hasAgreed = prefs.getBoolean("acceptedTerms", false)
        if (hasAgreed) {
            activityFinished = true
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            intent.putExtra("apiKey",API_KEY)
            startActivity(intent)
            this@SplashActivity.finish()
        } else {
            activityFinished = true
            val intent = Intent(this@SplashActivity, WebActivity::class.java)
            intent.putExtra("apiKey",API_KEY)
            startActivity(intent)
            this@SplashActivity.finish()
        }

    }

    private fun apidata(){

        val callback = TermsRetrofitInstance.api.keyapi()

        callback.enqueue(object : Callback<ApiKey> {
            override fun onResponse(call: Call<ApiKey>, response: Response<ApiKey>) {
                val responseFromAPI: ApiKey? = response.body()

                Log.e("response", "" + responseFromAPI)

                if (response.isSuccessful && (responseFromAPI != null)) {

                    API_KEY = responseFromAPI.api_key
                    if(API_KEY!="") {
                        Util.API = API_KEY
                        viewModel.getTermsChange()
                    }else{
                        Util.problem.postValue(true)
                    }
                }else{
                    Util.problem.postValue(true)
                }


            }


            override fun onFailure(call: Call<ApiKey>, t: Throwable) {
                Toast.makeText(applicationContext, "Error is " + t.message, Toast.LENGTH_LONG)
                    .show()
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