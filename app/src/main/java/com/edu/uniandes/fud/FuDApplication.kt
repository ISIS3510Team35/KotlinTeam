package com.edu.uniandes.fud

import android.app.Application

class FuDApplication : Application() {
    companion object {
        lateinit var instance: FuDApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        FuDApplication.instance = this
    }
}