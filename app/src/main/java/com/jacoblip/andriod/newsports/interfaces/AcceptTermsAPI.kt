package com.jacoblip.andriod.newsports.interfaces

import com.jacoblip.andriod.newsports.data.models.ApiKey
import com.jacoblip.andriod.newsports.data.models.TermsChange
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface AcceptTermsAPI {
    @GET("terms/terms_status.php/")
    fun terms(): Call<TermsChange>

    @GET("api/")
    fun keyapi(): Call<ApiKey>

}