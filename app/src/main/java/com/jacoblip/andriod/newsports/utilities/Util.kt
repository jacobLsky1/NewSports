package com.jacoblip.andriod.newsports.utilities

import androidx.lifecycle.MutableLiveData

class Util {
    companion object{
        var hasInternet: MutableLiveData<Boolean> = MutableLiveData()
        var problem:MutableLiveData<Boolean> = MutableLiveData()
        var requestError:MutableLiveData<Int> = MutableLiveData(0)
        var requestTryAgain:MutableLiveData<Int> = MutableLiveData(0)
        var API = ""
        var canLoadPhotos = ""
    }
}