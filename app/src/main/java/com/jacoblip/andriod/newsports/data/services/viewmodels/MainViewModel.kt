package com.jacoblip.andriod.newsports.data.services.viewmodels

import android.content.Context
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacoblip.andriod.newsports.data.models.callbacks.MatchesCallback
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



    fun loadMatchesFromServer(from:String,to:String){
        val callback = MainRetrofitInstance.api.fixturesBetweenDates(from, to)
        callback.enqueue(object : Callback<MatchesCallback> {
            override fun onFailure(call: Call<MatchesCallback>, t: Throwable) {
                Util.requestError.postValue(1)
            }

            override fun onResponse(
                call: Call<MatchesCallback>,
                response: Response<MatchesCallback>
            ) {
                if (response.isSuccessful) {
                    val matches = response.body()
                    if (matches != null) {
                        if (matches.data != null) {
                            if (matches.data.isNotEmpty()) {

                            } else {

                            }
                        }
                    }
                } else {

                }
            }
        })
    }
}