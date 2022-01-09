package com.jacoblip.andriod.newsports.ui.main

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jacoblip.andriod.newsports.R
import com.jacoblip.andriod.newsports.data.models.callbacks.MatchesCallback
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture
import com.jacoblip.andriod.newsports.data.models.leagues.CustomLeague
import com.jacoblip.andriod.newsports.databinding.ActivityDateMatchesBinding
import com.jacoblip.andriod.newsports.interfaces.MainRetrofitInstance
import com.jacoblip.andriod.newsports.ui.adapters.rv_adapters.main.SoccerMatchesAdapter
import com.jacoblip.andriod.newsports.utilities.Util
import com.jacoblip.andriod.newsports.utilities.internet.InternetConnectivity
import com.jacoblip.andriod.newsports.utilities.internet.WifiReceiver
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate


class DateMatchesActivity : AppCompatActivity() {

    private lateinit var wifiReceiver: WifiReceiver
    private lateinit var fragment: Fragment
    private lateinit var binding: ActivityDateMatchesBinding
    var errorDialogIsShowing = false
    var hasHappened = false

    private var _listOfMatchesForDate: MutableLiveData<List<CustomLeague>> = MutableLiveData()
    var  listOfMatchesForDate: LiveData<List<CustomLeague>> = _listOfMatchesForDate

    private var _isFetchingData:MutableLiveData<Boolean> = MutableLiveData(false)
    var isFetchingData:LiveData<Boolean> = _isFetchingData

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDateMatchesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpObservers(this.findViewById(android.R.id.content)/*gets the content view*/)
        wifiReceiver = WifiReceiver()
        setSupportActionBar(binding.toolbar)
       // if (supportActionBar != null) {
       //     supportActionBar!!.setDisplayHomeAsUpEnabled(true)
       // }

        val date = intent.getStringExtra("date")
        val now = LocalDate.now()
        hasHappened = LocalDate.parse(date).isBefore(now)
        binding.toolbarTitle.text = date
        binding.datematchesRV.layoutManager = LinearLayoutManager(applicationContext)

        loadMatchesByDateFromServer(date!!)
    }


    fun setUpObservers(view: View){
        val snackBar: Snackbar =
            Snackbar.make(view, "Can't Connect To Web..", Snackbar.LENGTH_INDEFINITE)
                .setAction("GO TO SETTINGS") {
                    this@DateMatchesActivity?.let { it1 -> InternetConnectivity.connectToInternet(applicationContext) }
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

        listOfMatchesForDate.observe(this,{
            if(!it.isNullOrEmpty()){
                binding.noDateDataTV.isVisible = false
                binding.datematchesRV.isVisible = true
                binding.datematchesRV.adapter = SoccerMatchesAdapter(it,applicationContext,hasHappened)
            }else{
                binding.noDateDataTV.isVisible = true
                binding.datematchesRV.isVisible = false
            }
        })

        isFetchingData.observe(this,{
            binding.progressBar3.isVisible = it
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

    fun makeErrorDialog(num: Int){
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.error_request_dalog, null)
        val checkInternetButton = dialogView.findViewById(R.id.checkInternetButton) as Button
        val yesButton = dialogView.findViewById(R.id.tryAgainButton) as Button

        val alertDialog = AlertDialog.Builder(this@DateMatchesActivity)
        alertDialog.setView(dialogView).setCancelable(true)

        val dialog = alertDialog.create()
        dialog.show()
        errorDialogIsShowing = true

        yesButton.setOnClickListener {
            dialog.dismiss()
            when(num){
                // TODO: 01/12/2021 depending what error there is try request again
                1->{}
                2->{}
                3->{}
                4->{}
                5->{}
            }
            errorDialogIsShowing = false
            Util.requestError.postValue(0)

        }

        checkInternetButton.setOnClickListener {
            this@DateMatchesActivity.let { InternetConnectivity.connectToInternet(applicationContext) }
        }
    }

    fun loadMatchesByDateFromServer(date:String){
        _isFetchingData.postValue(true)
        val callback = MainRetrofitInstance.api.fixturesForDate(date)
        callback.enqueue(object : Callback<MatchesCallback> {
            override fun onFailure(call: Call<MatchesCallback>, t: Throwable) {
                Util.requestError.postValue(1)
                _isFetchingData.postValue(false)
            }

            override fun onResponse(
                call: Call<MatchesCallback>,
                response: Response<MatchesCallback>
            ) {
                _isFetchingData.postValue(false)
                if (response.isSuccessful) {
                    val matches = response.body()
                    if (matches != null) {
                        if (matches.data != null) {
                            if (matches.data.isNotEmpty()) {
                                var list = sortDataToLeagues(matches.data)
                                _listOfMatchesForDate.postValue(list)
                            } else {
// TODO: 02/01/2022 handle error
                            }
                        }
                    }
                } else {
// TODO: 02/01/2022 handle error
                }
            }
        })
    }

    private fun sortDataToLeagues(matches:List<Fixture>):List<CustomLeague>{
        val groupedId = matches.groupBy { it.league_id }

        val leagues: ArrayList<CustomLeague>? = arrayListOf()
        groupedId.forEach { (_, fixtures) ->
            val league = fixtures[0].league!!.data
            if (fixtures[0].round != null) {
                league.round = fixtures[0].round!!.data
            }
            league.fixtures = fixtures.sortedByDescending { it.id }
            leagues!!.add(league)
        }

        if (leagues != null) {
            return leagues.toList()
        }
        return emptyList()
    }

}
