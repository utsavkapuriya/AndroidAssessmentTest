package com.test.androidassesmenttest.service

import com.test.androidassesmenttest.model.ResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceEndpointInterface {
    @GET("places/search")
    fun getLocationsBySearch(@Query("near") near: String): Call<ResponseData>
}