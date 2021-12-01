package com.test.androidassesmenttest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.test.androidassesmenttest.model.Resource
import com.test.androidassesmenttest.service.LocationRepository
import kotlinx.coroutines.Dispatchers
import java.net.UnknownHostException

class LocationViewModel(private val locationRepository: LocationRepository) : ViewModel() {

    fun getLocations(input: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = locationRepository.getLocationList(input)))
        } catch (networkException: UnknownHostException) {
            emit(Resource.error(data = null, message = "No Internet connection!"))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}