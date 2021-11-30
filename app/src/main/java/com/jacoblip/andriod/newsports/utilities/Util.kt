package com.jacoblip.andriod.newsports.utilities

import androidx.lifecycle.MutableLiveData

class Util {
    companion object{
        var hasInternet: MutableLiveData<Boolean> = MutableLiveData()
        var problem:MutableLiveData<Boolean> = MutableLiveData()
    }
}