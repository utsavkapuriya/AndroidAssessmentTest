package com.test.androidassesmenttest.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseData(
    val results: List<Result>
) : Parcelable