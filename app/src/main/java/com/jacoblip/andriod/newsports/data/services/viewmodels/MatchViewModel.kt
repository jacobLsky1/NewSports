package com.jacoblip.andriod.newsports.data.services.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jacoblip.andriod.newsports.data.models.callbacks.CommentariesCallback
import com.jacoblip.andriod.newsports.data.models.callbacks.MatchCallback
import com.jacoblip.andriod.newsports.data.models.callbacks.MatchesCallback
import com.jacoblip.andriod.newsports.data.models.callbacks.StandingsCallback
import com.jacoblip.andriod.newsports.data.models.commentaries.Commentary
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture
import com.jacoblip.andriod.newsports.data.models.standing.Standing
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

    private var _isFetchingData:MutableLiveData<Boolean> = MutableLiveData(false)
    var isFetchingData:LiveData<Boolean> = _isFetchingData

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
                    val match = response.body()!!.data
                    _matchCommentary.postValue(match)
                } else {
                    // TODO: 02/01/2022 handle error
                }
            }
        })
    }
}