package com.jacoblip.andriod.newsports.data.services.viewmodels


import com.jacoblip.andriod.newsports.data.models.TermsChange
import com.jacoblip.andriod.newsports.interfaces.TermsRetrofitInstance
import retrofit2.Call

class SplashRepository {

    suspend fun getTerms(): Call<TermsChange> {
        return TermsRetrofitInstance.api.terms()
    }

}