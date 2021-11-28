package com.test.androidassesmenttest

import android.app.Application
import android.content.Context
import com.test.androidassesmenttest.client.ApiManager.Companion.instance
import com.test.androidassesmenttest.client.ApiManager

class AssesmentApplication : Application() {

    init {
        application = this
    }
    override fun onCreate() {
        super.onCreate()
        apiManager = instance
    }

    companion object {
        var apiManager: ApiManager? = null
        private var application: AssesmentApplication? = null

        fun applicationContext() : Context {
            return application!!.applicationContext
        }
    }
}