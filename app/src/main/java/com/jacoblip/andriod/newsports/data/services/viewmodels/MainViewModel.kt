package com.jacoblip.andriod.newsports.data.services.viewmodels

import android.content.Context
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacoblip.andriod.newsports.data.models.callbacks.CountriesCallback
import com.jacoblip.andriod.newsports.data.models.callbacks.MatchesCallback
import com.jacoblip.andriod.newsports.data.models.country.Country
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture
import com.jacoblip.andriod.newsports.data.models.leagues.CustomLeague
import com.jacoblip.andriod.newsports.data.services.repositorys.MainRepository
import com.jacoblip.andriod.newsports.interfaces.MainRetrofitInstance
import com.jacoblip.andriod.newsports.ui.main.fragments.matches.MatchesMainFragment
import com.jacoblip.andriod.newsports.utilities.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val context: Context, private val repository: MainRepository
):ViewModel() {

    var currentMainFragment :Int? = null

    private var _mainFragmentCurrentFragment:MutableLiveData<Fragment> = MutableLiveData(
        MatchesMainFragment.newInstance())
    var mainFragmentCurrentFragment: LiveData<Fragment> = _mainFragmentCurrentFragment

    private var _listOfUpcomingMatches:MutableLiveData<List<CustomLeague>> = MutableLiveData()
    var  listOfUpcomingMatches:LiveData<List<CustomLeague>> = _listOfUpcomingMatches

    private var _listOfRecentMatches:MutableLiveData<List<CustomLeague>> = MutableLiveData()
    var  listOfRecentMatches:LiveData<List<CustomLeague>> = _listOfRecentMatches

    private var _listOfLiveMatches:MutableLiveData<List<CustomLeague>> = MutableLiveData()
    var  listOfLiveMatches:LiveData<List<CustomLeague>> = _listOfRecentMatches

    private var _listOfAllCountries:MutableLiveData<List<Country>> = MutableLiveData()
    var  listOfAllCountries:LiveData<List<Country>> = _listOfAllCountries

    private var _isFetchingData:MutableLiveData<Boolean> = MutableLiveData(false)
    var isFetchingData:LiveData<Boolean> = _isFetchingData



    fun loadUpcomingMatchesFromServer(from:String,to:String){
        _isFetchingData.postValue(true)
        val callback = MainRetrofitInstance.api.fixturesBetweenDates(from, to)
        callback.enqueue(object : Callback<MatchesCallback> {
            override fun onFailure(call: Call<MatchesCallback>, t: Throwable) {
                _isFetchingData.postValue(false)
                Util.requestError.postValue(2)
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
                                _listOfUpcomingMatches.postValue(list)
                            } else {
                                Util.requestError.postValue(2)
                            }
                        }
                    }
                } else {
                    Util.requestError.postValue(2)
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
            league.fixtures = fixtures.sortedBy { it.time.starting_at.date_time }
            leagues!!.add(league)
        }

        if (leagues != null) {
            return leagues.toList()
        }
        return emptyList()
    }

    fun loadRecentMatchesFromServer(from:String,to:String){
        _isFetchingData.postValue(true)
        val callback = MainRetrofitInstance.api.fixturesBetweenDates(from, to)
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
                                _listOfRecentMatches.postValue(list)
                            } else {
                                Util.requestError.postValue(1)
                            }
                        }
                    }
                } else {
                    Util.requestError.postValue(1)
                }
            }
        })
    }

    fun getAllCountriesFromServer(){
        _isFetchingData.postValue(true)
        val callback = MainRetrofitInstance.api.countries()
        callback.enqueue(object : Callback<CountriesCallback> {
            override fun onFailure(call: Call<CountriesCallback>, t: Throwable) {
                Util.requestError.postValue(1)
                _isFetchingData.postValue(false)
            }

            override fun onResponse(
                call: Call<CountriesCallback>,
                response: Response<CountriesCallback>
            ) {
                _isFetchingData.postValue(false)
                if (response.isSuccessful) {
                    val countries = response.body()
                    if (countries != null) {
                        if (countries.data != null) {
                            if (countries.data.isNotEmpty()) {
                                _listOfAllCountries.postValue(countries.data)
                            } else {
                                Util.requestError.postValue(1)
                            }
                        }
                    }
                } else {
                    Util.requestError.postValue(1)
                }
            }
        })
    }

    fun loadLiveMatchesFromServer(){
        _isFetchingData.postValue(true)
        val callback = MainRetrofitInstance.api.livescoresNow()
        callback.enqueue(object : Callback<MatchesCallback> {
            override fun onFailure(call: Call<MatchesCallback>, t: Throwable) {
                _isFetchingData.postValue(false)
                Util.requestError.postValue(15)
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
                                _listOfLiveMatches.postValue(list)
                            } else {
                                Util.requestError.postValue(15)
                            }
                        }
                    }
                } else {
                    Util.requestError.postValue(15)
                }
            }
        })
    }


    fun loadGameHighLightsFromServer(){
        _isFetchingData.postValue(true)
        val callback = MainRetrofitInstance.api.
        callback.enqueue(object : Callback<MatchesCallback> {
            override fun onFailure(call: Call<MatchesCallback>, t: Throwable) {
                _isFetchingData.postValue(false)
                Util.requestError.postValue(15)
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
                                _listOfLiveMatches.postValue(list)
                            } else {
                                Util.requestError.postValue(15)
                            }
                        }
                    }
                } else {
                    Util.requestError.postValue(15)
                }
            }
        })
    }



}