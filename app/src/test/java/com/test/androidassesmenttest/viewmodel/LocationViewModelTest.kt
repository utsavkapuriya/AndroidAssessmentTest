package com.test.androidassesmenttest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.test.androidassesmenttest.model.Resource
import com.test.androidassesmenttest.model.ResponseData
import com.test.androidassesmenttest.model.Result
import com.test.androidassesmenttest.service.LocationRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LocationViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var locationRepository: LocationRepository

    @Mock
    private lateinit var observer: Observer<Resource<ResponseData>>

    @Mock
    private val responseData: ResponseData = ResponseData(listOf<Result>())

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            doReturn(responseData)
                .`when`(locationRepository)
                .getLocationList("")
            val viewModel = LocationViewModel(locationRepository)
            viewModel.getLocations("").observeForever(observer)
            verify(locationRepository).getLocationList("")
            verify(observer).onChanged(Resource.success(responseData))
            viewModel.getLocations("").removeObserver(observer)
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message For You"
            doThrow(RuntimeException(errorMessage))
                .`when`(locationRepository)
                .getLocationList("")
            val viewModel = LocationViewModel(locationRepository)
            viewModel.getLocations("").observeForever(observer)
            verify(locationRepository).getLocationList("")
            verify(observer).onChanged(Resource.error(null, RuntimeException(errorMessage).toString()))
            viewModel.getLocations("").removeObserver(observer)
        }
    }

}