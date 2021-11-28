package com.test.androidassesmenttest.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val address: String,
    val country: String,
    val cross_street: String,
    val locality: String,
    val neighborhood: List<String>,
    val postcode: String,
    val region: String
): Parcelable