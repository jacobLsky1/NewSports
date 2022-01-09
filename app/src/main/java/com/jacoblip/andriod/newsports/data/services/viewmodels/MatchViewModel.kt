package com.jacoblip.andriod.newsports.data.services.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jacoblip.andriod.newsports.data.models.callbacks.*
import com.jacoblip.andriod.newsports.data.models.commentaries.Commentary
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture
import com.jacoblip.andriod.newsports.data.models.standing.Standing
import com.jacoblip.andriod.newsports.data.models.team.Team
import com.jacoblip.andriod.newsports.data.services.repositorys.MainRepository
import com.jacoblip.andriod.newsports.data.services.repositorys.MatchRepository
import com.jacoblip.andriod.newsports.interfaces.MainRetrofitInstance
import com.jacoblip.andriod.newsports.utilities.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MatchViewModel@Inject constructor(
    private val context: Context, private val repository: MatchRepository
): ViewModel() {

    private var _match:MutableLiveData<Fixture> = MutableLiveData()
    var match :LiveData<Fixture> = _match

    private var _matchCommentary:MutableLiveData<ArrayList<Commentary>> = MutableLiveData()
    var matchCommentary :LiveData<ArrayList<Commentary>> = _matchCommentary

    private var _matchStandings:MutableLiveData<ArrayList<Standing>> = MutableLiveData()
    var matchStandings :LiveData<ArrayList<Standing>> = _matchStandings

    private var _listOfH2HMatches:MutableLiveData<List<Fixture>> = MutableLiveData()
    var  listOfH2HMatches:LiveData<List<Fixture>> = _listOfH2HMatches

    private var _isFetchingData:MutableLiveData<Boolean> = MutableLiveData(false)
    var isFetchingData:LiveData<Boolean> = _isFetchingData

    private var _homeTeamDetails:MutableLiveData<Team> = MutableLiveData()
     var homeTeamDetails:LiveData<Team> = _homeTeamDetails

    private var _visitorTeamDetails:MutableLiveData<Team> = MutableLiveData()
    var visitorTeamDetails:LiveData<Team> = _visitorTeamDetails

    fun getMatchInQuestion(matchId:Long){
        _isFetchingData.postValue(true)
        val callback = MainRetrofitInstance.api.fixturesById(matchId)
        callback.enqueue(object : Callback<MatchCallback> {
            override fun onFailure(call: Call<MatchCallback>, t: Throwable) {
                _isFetchingData.postValue(false)
                Util.requestError.postValue(1)
            }

            override fun onResponse(
                call: Call<MatchCallback>,
                response: Response<MatchCallback>
            ) {
                _isFetchingData.postValue(false)
                if (response.isSuccessful) {
                    val match = response.body()!!.data
                    _match.postValue(match)
                } else {
                    // TODO: 02/01/2022 handle error
                }
            }
        })
    }

    fun getMatchStandings(seasonId:Long){
        _isFetchingData.postValue(true)
        val callback = MainRetrofitInstance.api.standings(seasonId)
        callback.enqueue(object : Callback<StandingsCallback> {
            override fun onFailure(call: Call<StandingsCallback>, t: Throwable) {
                _isFetchingData.postValue(false)
                Util.requestError.postValue(1)
            }

            override fun onResponse(
                call: Call<StandingsCallback>,
                response: Response<StandingsCallback>
            ) {
                _isFetchingData.postValue(false)
                if (response.isSuccessful) {
                    val match = response.body()!!.data
                    _matchStandings.postValue(match)
                } else {
                    // TODO: 02/01/2022 handle error
                }
            }
        })
    }

    fun getMatchCommentary(matchId:Long){
        _isFetchingData.postValue(true)
        val callback = MainRetrofitInstance.api.commentary(matchId)
        callback.enqueue(object : Callback<CommentariesCallback> {
            override fun onFailure(call: Call<CommentariesCallback>, t: Throwable) {
                _isFetchingData.postValue(false)
                Util.requestError.postValue(1)
            }

            override fun onResponse(
                call: Call<CommentariesCallback>,
                response: Response<CommentariesCallback>
            ) {
                _isFetchingData.postValue(false)
                if (response.isSuccessful) {
                    val match = response.body()!!.data.reversed()
                    val allCommentary = arrayListOf<Commentary>()
                    allCommentary.addAll(match)
                    _matchCommentary.postValue(allCommentary)
                } else {
                    // TODO: 02/01/2022 handle error
                }
            }
        })
    }

    fun getH2Hmatches(match:Fixture){
        _isFetchingData.postValue(true)
        val callback = MainRetrofitInstance.api.getH2H(match.localteam_id, match.visitorteam_id)
        callback.enqueue(object : Callback<MatchesCallback> {
            override fun onFailure(call: Call<MatchesCallback>, t: Throwable) {
                _isFetchingData.postValue(false)
                Util.requestError.postValue(1)
            }

            override fun onResponse(
                call: Call<MatchesCallback>,
                response: Response<MatchesCallback>
            ) {
                _isFetchingData.postValue(false)
                if (response.isSuccessful) {
                    val matches = response.body()!!.data
                    _listOfH2HMatches.postValue(matches)
                } else {
                    // TODO: 02/01/2022 handle error
                }
            }
        })
    }

    fun fetchHomeTeamDetailFromAPI(teamId:Long){
        _isFetchingData.postValue(true)
        val callback = MainRetrofitInstance.api.teamResultsById(teamId)
        callback.enqueue(object : Callback<TeamCallback> {
            override fun onFailure(call: Call<TeamCallback>, t: Throwable) {
                _isFetchingData.postValue(false)
                Util.requestError.postValue(1)
            }

            override fun onResponse(
                call: Call<TeamCallback>,
                response: Response<TeamCallback>
            ) {
                _isFetchingData.postValue(false)
                if (response.isSuccessful) {
                    val team = response.body()!!.data
                    _homeTeamDetails.postValue(team)
                } else {
                    // TODO: 02/01/2022 handle error
                }
            }
        })
    }

    fun fetchVisitorTeamDetailFromAPI(teamId:Long){
        _isFetchingData.postValue(true)
        val callback = MainRetrofitInstance.api.teamResultsById(teamId)
        callback.enqueue(object : Callback<TeamCallback> {
            override fun onFailure(call: Call<TeamCallback>, t: Throwable) {
                _isFetchingData.postValue(false)
                Util.requestError.postValue(1)
            }

            override fun onResponse(
                call: Call<TeamCallback>,
                response: Response<TeamCallback>
            ) {
                _isFetchingData.postValue(false)
                if (response.isSuccessful) {
                    val team = response.body()!!.data
                    _visitorTeamDetails.postValue(team)
                } else {
                    // TODO: 02/01/2022 handle error
                }
            }
        })
    }
}