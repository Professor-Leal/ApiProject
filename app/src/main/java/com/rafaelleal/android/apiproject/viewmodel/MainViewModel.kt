package com.rafaelleal.android.apiproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelleal.android.apiproject.api.models.Article
import com.rafaelleal.android.apiproject.api.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {

    val repositorio = NewsRepository.get()
    // Uma alternativa é usar o Hilt para injeção de dependência


    private val _selectedArticle: MutableLiveData<Article> by lazy {
        MutableLiveData<Article>()
    }

    val selectedArticle: LiveData<Article> = _selectedArticle

    fun setSelectedArticle(value: Article){
        _selectedArticle.postValue(value)
    }

    private val _topHeadlinesArticles: MutableStateFlow<List<Article>> =
        MutableStateFlow(emptyList())
    val topHeadlinesArticles: StateFlow<List<Article>>
        get() = _topHeadlinesArticles.asStateFlow()

    private val _newsArticles: MutableStateFlow<List<Article>> =
        MutableStateFlow(emptyList())
    val newsArticles: StateFlow<List<Article>>
        get() = _newsArticles.asStateFlow()

    fun collectTopHeadlines() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val items = fetchTopHeadlines()
                _topHeadlinesArticles.value = items
            } catch (ex: Exception) {
                Log.e(TAG, "Failed to fetch news items", ex)
            }
        }
    }

    fun collectNews(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val items = fetchNewsContets(query)
                _newsArticles.value = items
            } catch (ex: Exception) {
                Log.e(TAG, "Failed to fetch news items", ex)
            }
        }
    }

    suspend fun fetchNewsContets(query: String): List<Article> {
        var lista : List<Article>

        try{
            val response = repositorio.fetchNewsContents(query)
            lista = response.articles ?: emptyList()
            Log.d(TAG, "Lista News: $lista")

        } catch (ex: Exception){
            lista = listOf()
            Log.e(TAG, "Falhou em receber dados da API", ex)
        }

        return lista
    }

    suspend fun fetchTopHeadlines(): List<Article> {
        var lista : List<Article>

        try{
            val response = repositorio.fetchNewsTopHeadlines()
            lista = response.articles ?: emptyList()
            Log.d(TAG, "Lista Top Headlines: $lista")

        } catch (ex: Exception){
            lista = listOf()
            Log.e(TAG, "Falhou em receber dados da API", ex)
        }
        return lista
    }

    init {
        collectTopHeadlines()
    }


}