package com.test.androidassesmenttest.model

sealed class Status {
    object SUCCESS: Status()
    object ERROR: Status()
    object LOADING: Status()
}