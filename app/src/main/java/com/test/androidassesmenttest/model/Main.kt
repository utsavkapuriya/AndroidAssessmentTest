package com.test.androidassesmenttest.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Main(
    val latitude: Double,
    val longitude: Double
) : Parcelable