package com.test.androidassesmenttest.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Icon(
    val prefix: String,
    val suffix: String
) : Parcelable