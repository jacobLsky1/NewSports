package com.jacoblip.andriod.newsports.data.services.viewmodels

import android.content.Context
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacoblip.andriod.newsports.data.models.callbacks.MatchesCallback
import com.jacoblip.andriod.newsports.data.models.fixture.Fixture
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

    private var _listOfUpcomingMatches:MutableLiveData<List<Fixture>> = MutableLiveData()
    var  listOfUpcomingMatches:LiveData<List<Fixture>> = _listOfUpcomingMatches

    private var _listOfRecentMatches:MutableLiveData<List<Fixture>> = MutableLiveData()
    var  listOfRecentMatches:LiveData<List<Fixture>> = _listOfRecentMatches

    private var _isFetchingData:MutableLiveData<Boolean> = MutableLiveData(false)
    var isFetchingData:LiveData<Boolean> = _isFetchingData


    fun loadUpcomingMatchesFromServer(from:String,to:String){
        _isFetchingData.postValue(true)
        val callback = MainRetrofitInstance.api.fixturesBetweenDates(from, to)
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
                    val matches = response.body()
                    if (matches != null) {
                        if (matches.data != null) {
                            if (matches.data.isNotEmpty()) {
                                var list = matches.data
                                list.sortBy { it.time.starting_at.date_time }
                                _listOfUpcomingMatches.postValue(list)
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
                                var list = matches.data
                                list.sortBy { it.time.starting_at.date_time }
                                _listOfRecentMatches.postValue(list.reversed())
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
}