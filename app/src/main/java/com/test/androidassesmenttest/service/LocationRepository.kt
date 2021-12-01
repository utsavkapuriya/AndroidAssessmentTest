package com.test.androidassesmenttest.service

class LocationRepository(private val endpointInterface: ServiceEndpointInterface) {
    suspend fun getLocationList(input: String) = endpointInterface.getLocationsBySearch(input)
}