package com.rafaelleal.android.apiproject.api.interfaces

import com.rafaelleal.android.apiproject.api.models.NewsApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


// Referência: https://newsapi.org/

const val API_KEY = "Sua Chave da API"
const val SIGLA_PAIS = "br"
const val LANGUAGE = "pt"

interface NewsApiDao {

    // https://newsapi.org/docs/endpoints/everything
    // É possível colocar mais filtros como data, lingua, país , etc
    @GET("/v2/everything?language=$LANGUAGE&apiKey=$API_KEY")
    suspend fun fetchContents(
        @Query("q") input: String,
    ): NewsApiResponse //String //List<Article>

    // https://newsapi.org/docs/endpoints/top-headlines
    @GET("/v2/top-headlines?country=$SIGLA_PAIS&apiKey=$API_KEY")
    suspend fun fetchTopHeadlines(): NewsApiResponse //String // List<Article>

}