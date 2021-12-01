package com.test.androidassesmenttest.client

import com.test.androidassesmenttest.AssesmentApplication
import com.test.androidassesmenttest.BuildConfig
import com.test.androidassesmenttest.service.ServiceEndpointInterface
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiManager private constructor() {
    var retrofitService: ServiceEndpointInterface

    companion object {
        private lateinit var apiManager: ApiManager
        private val BASE_URL = "https://api.foursquare.com/v3/"
        val authorization: String = BuildConfig.FOURSQUARE_API_KEY
        val instance: ApiManager
            get() {
                if (!::apiManager.isInitialized) {
                    ApiManager().also { apiManager = it }
                }
                return apiManager
            }
    }

    init {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor { chain ->
            val original: Request = chain.request()

            val request: Request = original.newBuilder()
                .header("Authorization", authorization)
                .header("Accept", "application/json")
                .method(original.method(), original.body())
                .build()
            chain.proceed(request)
        }
        httpClient.addNetworkInterceptor(NetworkConnectionInterceptor(AssesmentApplication.applicationContext()))

        val client = httpClient.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        retrofitService = retrofit.create(ServiceEndpointInterface::class.java)
    }
}