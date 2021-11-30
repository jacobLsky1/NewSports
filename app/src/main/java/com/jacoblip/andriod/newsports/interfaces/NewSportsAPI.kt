package com.jacoblip.andriod.newsports.interfaces


import retrofit2.Response
import retrofit2.http.GET

interface NewSportsAPI {

    // TODO: 25/10/2021 import data objects
    @GET("terms")
    suspend fun getTermsOK():Response<String>
}