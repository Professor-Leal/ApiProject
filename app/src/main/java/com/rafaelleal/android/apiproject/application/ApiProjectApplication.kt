package com.rafaelleal.android.apiproject.application

import android.app.Application
import com.rafaelleal.android.apiproject.api.repository.NewsRepository

class ApiProjectApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NewsRepository.initialize()
    }
}