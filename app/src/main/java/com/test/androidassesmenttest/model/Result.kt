package com.test.androidassesmenttest.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    val distance: Int,
    val fsq_id: String,
    val location: Location,
    val name: String,
    val timezone: String,
    val description: String,
    val rating: Int,
    val photos: List<Photo>
) : Parcelable