package com.jacoblip.andriod.newsports.data.services.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jacoblip.andriod.newsports.data.models.callbacks.MatchCallback
import com.jacoblip.andriod.newsports.data.models.callbacks.TeamCallback
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture
import com.jacoblip.andriod.newsports.data.models.seasons.players.PlayerRanking
import com.jacoblip.andriod.newsports.data.models.team.Team
import com.jacoblip.andriod.newsports.data.services.repositorys.MatchRepository
import com.jacoblip.andriod.newsports.data.services.repositorys.TeamRepository
import com.jacoblip.andriod.newsports.interfaces.MainRetrofitInstance
import com.jacoblip.andriod.newsports.utilities.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TeamViewModel@Inject constructor(
    private val context: Context, private val repository: TeamRepository
): ViewModel() {

    private var _team: MutableLiveData<Team> = MutableLiveData()
    var team: LiveData<Team> = _team

    private var _upComingMatch: MutableLiveData<Fixture> = MutableLiveData()
    var upComingMatch: LiveData<Fixture> = _upComingMatch

    private var _preMatch: MutableLiveData<Fixture> = MutableLiveData()
    var preMatch: LiveData<Fixture> = _preMatch

    private var _allTeamMatches:MutableLiveData<List<Fixture>> = MutableLiveData()
    var allTeamMatches:LiveData<List<Fixture>> = _allTeamMatches

    private var _upcomingTeamMatches:MutableLiveData<List<Fixture>> = MutableLiveData()
    var upcomingTeamMatches:LiveData<List<Fixture>> = _upcomingTeamMatches

    private var _teamSquad:MutableLiveData<List<PlayerRanking>> = MutableLiveData()
    var teamSquad:LiveData<List<PlayerRanking>> = _teamSquad

    private var _isFetchingData:MutableLiveData<Boolean> = MutableLiveData(false)
    var isFetchingData:LiveData<Boolean> = _isFetchingData

    fun getTeamById(teamId:Long){
        _isFetchingData.postValue(true)
        val callback = MainRetrofitInstance.api.teamOverviewById(teamId,Util.API)
        callback.enqueue(object : Callback<TeamCallback> {
            override fun onFailure(call: Call<TeamCallback>, t: Throwable) {
                _isFetchingData.postValue(false)
                Util.requestError.postValue(3)
            }

            override fun onResponse(
                call: Call<TeamCallback>,
                response: Response<TeamCallback>
            ) {
                _isFetchingData.postValue(false)
                if (response.isSuccessful) {
                    val responseTeam = response.body()!!.data
                    _team.postValue(responseTeam)
                } else {
                    Util.requestError.postValue(3)
                }
            }
        })
    }

    fun getTeamMatchesById(teamId:Long){
        _isFetchingData.postValue(true)
        val callback = MainRetrofitInstance.api.teamFixturesById(teamId,Util.API)
        callback.enqueue(object : Callback<TeamCallback> {
            override fun onFailure(call: Call<TeamCallback>, t: Throwable) {
                _isFetchingData.postValue(false)
                Util.requestError.postValue(4)
            }

            override fun onResponse(
                call: Call<TeamCallback>,
                response: Response<TeamCallback>
            ) {
                _isFetchingData.postValue(false)
                if (response.isSuccessful) {
                    val responseMatch = response.body()!!.data.upcoming.data[0]
                    _upComingMatch.postValue(responseMatch)
                    val responsePreMatch = response.body()!!.data.latest.data[0]
                    _preMatch.postValue(responsePreMatch)
                } else {
                    Util.requestError.postValue(4)
                }
            }
        })
    }

    fun fetchResultsForTeam(teamId:Long,){
        _isFetchingData.postValue(true)
        val callback = MainRetrofitInstance.api.teamFixturesById(teamId,Util.API)
        callback.enqueue(object : Callback<TeamCallback> {
            override fun onFailure(call: Call<TeamCallback>, t: Throwable) {
                _isFetchingData.postValue(false)
                Util.requestError.postValue(5)
            }

            override fun onResponse(
                call: Call<TeamCallback>,
                response: Response<TeamCallback>
            ) {
                _isFetchingData.postValue(false)
                if (response.isSuccessful) {
                    val allFixtures: ArrayList<Fixture> = arrayListOf()
                    allFixtures.addAll(response.body()!!.data.latest.data)
                    _allTeamMatches.postValue(allFixtures)
                } else {
                    Util.requestError.postValue(5)
                }
            }
        })
    }

    fun fetchUpcomingMatchesForTeam(teamId:Long,){
        _isFetchingData.postValue(true)
        val callback = MainRetrofitInstance.api.teamFixturesById(teamId,Util.API)
        callback.enqueue(object : Callback<TeamCallback> {
            override fun onFailure(call: Call<TeamCallback>, t: Throwable) {
                _isFetchingData.postValue(false)
                Util.requestError.postValue(6)
            }

            override fun onResponse(
                call: Call<TeamCallback>,
                response: Response<TeamCallback>
            ) {
                _isFetchingData.postValue(false)
                if (response.isSuccessful) {
                    val allFixtures: ArrayList<Fixture> = arrayListOf()
                    allFixtures.addAll(response.body()!!.data.upcoming.data)
                    _upcomingTeamMatches.postValue(allFixtures)
                } else {
                    Util.requestError.postValue(6)
                }
            }
        })
    }

    fun fetchSquadForTeam(teamId:Long,){
        _isFetchingData.postValue(true)
        val callback = MainRetrofitInstance.api.teamSquadById(teamId,Util.API)
        callback.enqueue(object : Callback<TeamCallback> {
            override fun onFailure(call: Call<TeamCallback>, t: Throwable) {
                _isFetchingData.postValue(false)
                Util.requestError.postValue(7)
            }

            override fun onResponse(
                call: Call<TeamCallback>,
                response: Response<TeamCallback>
            ) {
                _isFetchingData.postValue(false)
                if (response.isSuccessful) {
                    val allPlayers: ArrayList<PlayerRanking> = arrayListOf()
                    allPlayers.addAll(response.body()!!.data.squad.data)
                    _teamSquad.postValue(allPlayers)
                } else {
                    Util.requestError.postValue(7)
                }
            }
        })
    }
}