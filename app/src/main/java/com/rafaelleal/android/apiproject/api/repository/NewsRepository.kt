package com.rafaelleal.android.apiproject.api.repository

import com.rafaelleal.android.apiproject.api.interfaces.NewsApiDao
import com.rafaelleal.android.apiproject.api.models.NewsApiResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class NewsRepository () {

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val newsApiDao: NewsApiDao = retrofit.create()

    companion object {
        private var INSTANCE: NewsRepository? = null

        fun initialize() {
            if (INSTANCE == null) {
                INSTANCE = NewsRepository()
            }
        }

        fun get(): NewsRepository {
            return INSTANCE
                ?: throw IllegalStateException("NewsRepository deve ser inicializado.")
        }
    }

    suspend fun fetchNewsContents(query: String): NewsApiResponse = newsApiDao.fetchContents(query)

    suspend fun fetchNewsTopHeadlines(): NewsApiResponse = newsApiDao.fetchTopHeadlines()


}