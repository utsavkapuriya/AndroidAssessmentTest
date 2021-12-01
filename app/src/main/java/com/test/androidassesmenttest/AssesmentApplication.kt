package com.test.androidassesmenttest

import android.app.Application
import android.content.Context

class AssesmentApplication : Application() {

    init {
        application = this
    }
    companion object {
        private var application: AssesmentApplication? = null

        fun applicationContext() : Context {
            return application!!.applicationContext
        }
    }
}