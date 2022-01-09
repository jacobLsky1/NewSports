package com.jacoblip.andriod.newsports.data.services.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.jacoblip.andriod.newsports.data.models.callbacks.SeasonMatchesCallback
import com.jacoblip.andriod.newsports.data.models.callbacks.SeasonTopScorersCallback
import com.jacoblip.andriod.newsports.data.models.callbacks.StandingsCallback
import com.jacoblip.andriod.newsports.data.models.leagues.CustomLeague
import com.jacoblip.andriod.newsports.data.models.leagues.LeagueRoundHeader
import com.jacoblip.andriod.newsports.data.models.seasons.SeasonMatches
import com.jacoblip.andriod.newsports.data.models.seasons.topscorers.TopScorer
import com.jacoblip.andriod.newsports.data.models.standing.Standing
import com.jacoblip.andriod.newsports.data.services.repositorys.LeaguesRepository
import com.jacoblip.andriod.newsports.interfaces.MainRetrofitInstance
import com.jacoblip.andriod.newsports.utilities.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LeaguesViewModel@Inject constructor(
    private val context: Context, private val repository: LeaguesRepository
): ViewModel() {


    private var _isFetchingData: MutableLiveData<Boolean> = MutableLiveData(false)
    var isFetchingData: LiveData<Boolean> = _isFetchingData

    private var _selectedLeague: MutableLiveData<CustomLeague> = MutableLiveData()
    var selectedLeague: LiveData<CustomLeague> = _selectedLeague

    private var _standings: MutableLiveData<List<Standing>> = MutableLiveData()
    var standings: LiveData<List<Standing>> = _standings

    private var _futureSeasonMatches: MutableLiveData<List<LeagueRoundHeader>> = MutableLiveData()
    var futureSeasonMatches: LiveData<List<LeagueRoundHeader>> = _futureSeasonMatches

    private var _pastSeasonMatches: MutableLiveData<List<LeagueRoundHeader>> = MutableLiveData()
    var pastSeasonMatches: LiveData<List<LeagueRoundHeader>> = _pastSeasonMatches

    private var _topScorers: MutableLiveData<TopScorer> = MutableLiveData()
    var topScorers: LiveData<TopScorer> = _topScorers

    fun getStandingsForSeason(seasonId:Long){
        val call = MainRetrofitInstance.api.standings(seasonId)
        call.enqueue( object : Callback<StandingsCallback> {
            override fun onFailure(call: Call<StandingsCallback>, t: Throwable) {
                _isFetchingData.postValue(false)
                Util.requestError.postValue(11)
            }

            override fun onResponse(call: Call<StandingsCallback>, response: Response<StandingsCallback>) {
                _isFetchingData.postValue(false)
                if (response.isSuccessful) {
                    val match = response.body()!!.data
                    _standings.postValue(match)
                } else {
                    Util.requestError.postValue(11)
                }
            }
        })
    }

    fun getTopScorersForSeason(seasonId:Long){
        val call = MainRetrofitInstance.api.seasonTopPlayers(seasonId)
        call.enqueue( object : Callback<SeasonTopScorersCallback> {
            override fun onFailure(call: Call<SeasonTopScorersCallback>, t: Throwable) {
                _isFetchingData.postValue(false)
                Util.requestError.postValue(12)
            }

            override fun onResponse(call: Call<SeasonTopScorersCallback>, response: Response<SeasonTopScorersCallback>) {
                _isFetchingData.postValue(false)
                if (response.isSuccessful) {
                    val match = response.body()!!.data
                    _topScorers.postValue(match)
                } else {
                    Util.requestError.postValue(12)
                }
            }
        })
    }


    fun getPassedSeasonMatches(url:String){
        val call = MainRetrofitInstance.api.seasonMatches(url)
        call.enqueue( object : Callback<SeasonMatchesCallback> {
            override fun onFailure(call: Call<SeasonMatchesCallback>, t: Throwable) {
                _isFetchingData.postValue(false)
                Util.requestError.postValue(13)
            }

            override fun onResponse(call: Call<SeasonMatchesCallback>, response: Response<SeasonMatchesCallback>) {
                _isFetchingData.postValue(false)
                if (response.isSuccessful) {
                    val responseMatchs = response.body()!!.data
                   val fixtures = responseMatchs.results.data.sortedByDescending { it.id }

                    val groupedId = fixtures.groupBy { it.round_id }
                    val round = responseMatchs.round?.data
                    if(round!=null) {
                        val leagues: ArrayList<LeagueRoundHeader> = arrayListOf()
                        groupedId.forEach { (roundId, fixtures) ->
                            val roundName = (roundId - round.id) + round.name
                            val league = LeagueRoundHeader(roundName.toString(), fixtures)
                            league.fixtures = fixtures.sortedByDescending { it.id }
                            leagues.add(league)
                        }
                        _pastSeasonMatches.postValue(leagues)
                    }else{
                        _pastSeasonMatches.postValue(emptyList())
                    }


                } else {
                    Util.requestError.postValue(13)
                }
            }
        })
    }

    fun getFutureSeasonMatches(url:String){
        val call = MainRetrofitInstance.api.seasonMatches(url)
        call.enqueue( object : Callback<SeasonMatchesCallback> {
            override fun onFailure(call: Call<SeasonMatchesCallback>, t: Throwable) {
                _isFetchingData.postValue(false)
                Util.requestError.postValue(14)
            }

            override fun onResponse(call: Call<SeasonMatchesCallback>, response: Response<SeasonMatchesCallback>) {
                _isFetchingData.postValue(false)
                if (response.isSuccessful) {
                    val responseMatchs = response.body()!!.data
                    val fixtures = responseMatchs.upcoming.data
                    val groupedId = fixtures.groupBy { it.round_id }
                    val round = responseMatchs.round?.data
                    if(round!=null) {
                        val leagues: ArrayList<LeagueRoundHeader> = arrayListOf()
                        groupedId.forEach { (roundId, fixtures) ->
                            val roundName = (roundId - round.id) + round.name
                            val league = LeagueRoundHeader(roundName.toString(), fixtures)
                            league.fixtures = fixtures.sortedByDescending { it.id }
                            leagues.add(league)
                        }
                        _futureSeasonMatches.postValue(leagues)
                    }else{
                        _futureSeasonMatches.postValue(emptyList())
                    }



                } else {
                    Util.requestError.postValue(14)
                }
            }
        })
    }

}