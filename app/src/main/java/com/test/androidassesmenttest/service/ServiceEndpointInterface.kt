package com.test.androidassesmenttest.service

import com.test.androidassesmenttest.model.ResponseData
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceEndpointInterface {
    @GET("places/search")
    suspend fun getLocationsBySearch(@Query("near") near: String): ResponseData
}