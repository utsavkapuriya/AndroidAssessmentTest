package com.test.androidassesmenttest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.androidassesmenttest.model.ResponseData
import com.test.androidassesmenttest.service.MainRepository
import retrofit2.Call
import retrofit2.Callback

class LocationViewModel constructor(private val repository: MainRepository) : ViewModel() {

    val locationList = MutableLiveData<ResponseData>()
    val errorMessage = MutableLiveData<String>()
    val isViewLoading = MutableLiveData<Boolean>()
    val isEmptyList = MutableLiveData<Boolean>()

    /**
     * This is used to get the location data from given query
     */
    fun getLocationList(input: String) {
        isViewLoading.value = true
        val response = repository.getLocationList(input)
        response?.enqueue(object : Callback<ResponseData> {
            override fun onResponse(
                call: Call<ResponseData>,
                responseData: retrofit2.Response<ResponseData>
            ) {
                //Todo enter data into the DB from here in room db
                val responseData = responseData.body()
                isViewLoading.value = false
                responseData?.let {
                    locationList.postValue(it)
                } ?: run{
                    isEmptyList.postValue(true)
                }
            }

            override fun onFailure(
                call: Call<ResponseData>,
                t: Throwable
            ) {
                //Todo check network error or not, if yes then get the data from DB and postvalue to activity
                errorMessage.postValue(t.message)
                isViewLoading.value = false
            }
        })
    }
}