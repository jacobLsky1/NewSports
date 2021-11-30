package com.jacoblip.andriod.newsports.data.services.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jacoblip.andriod.newsports.data.models.TermsChange
import com.jacoblip.andriod.newsports.data.services.repositorys.MainRepository
import com.jacoblip.andriod.newsports.data.services.repositorys.SplashRepository
import com.jacoblip.andriod.newsports.interfaces.TermsRetrofitInstance
import com.jacoblip.andriod.newsports.utilities.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TermsViewModel
@Inject constructor(
    private val context: Context, private val repository: SplashRepository
):ViewModel() {

    private var _termsChanged: MutableLiveData<Boolean> = MutableLiveData()
    var termsChanged: LiveData<Boolean> = _termsChanged
    private var _canAccess: MutableLiveData<Boolean> = MutableLiveData()
    var canAccess: LiveData<Boolean> = _canAccess


    fun getTermsChange(){
        val callback = TermsRetrofitInstance.api.terms()

        callback.enqueue(object : Callback<TermsChange> {
            override fun onResponse(call: Call<TermsChange>, response: Response<TermsChange>) {
                val responseFromAPI: TermsChange? = response.body()

                Log.e("response", "" + responseFromAPI)

                if (response.isSuccessful && (responseFromAPI != null)) {

                    val change = responseFromAPI.terms_status
                    if(change!="") {
                        _termsChanged.postValue(true)
                    }else{
                        _termsChanged.postValue(false)
                    }
                }else{
                    Util.problem.postValue(true)
                }


            }


            override fun onFailure(call: Call<TermsChange>, t: Throwable) {
                Toast.makeText(context, "Error is " + t.message, Toast.LENGTH_LONG)
                    .show()
            }


        })
    }

    fun setAccess(){
        _canAccess.postValue(true)
    }

}