package com.mambobryan.samba

import android.app.Application
import android.content.pm.ApplicationInfo
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App: Application() {


    override fun onCreate() {
        super.onCreate()

        initTimber()

    }

    private fun initTimber() {
        val isDebuggable = 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
        if (isDebuggable) Timber.plant(Timber.DebugTree())
    }
    
}