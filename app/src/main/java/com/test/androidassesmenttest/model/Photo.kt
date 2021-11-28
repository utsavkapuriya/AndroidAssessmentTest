package com.test.androidassesmenttest.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    val classifications: List<String>,
    val created_at: String,
    val height: Int,
    val id: String,
    val prefix: String,
    val suffix: String,
    val width: Int
): Parcelable