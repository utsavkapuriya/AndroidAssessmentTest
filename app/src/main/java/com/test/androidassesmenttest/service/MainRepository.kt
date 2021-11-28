package com.test.androidassesmenttest.service

class MainRepository constructor(private val endpointInterface: ServiceEndpointInterface?) {

    fun getLocationList(input: String) = endpointInterface?.getLocationsBySearch(input)
}