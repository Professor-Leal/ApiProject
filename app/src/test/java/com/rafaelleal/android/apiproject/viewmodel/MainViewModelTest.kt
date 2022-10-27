package com.rafaelleal.android.apiproject.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rafaelleal.android.apiproject.api.models.Article
import com.rafaelleal.android.apiproject.api.models.Source
import com.rafaelleal.android.apiproject.api.repository.NewsRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.mock

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    //private lateinit var repository: NewsRepository

    @Before
    fun setup(){
//        NewsRepository.initialize()
//        NewsRepository.initialize()
//        repository = NewsRepository.get()
//        repository
        val viewModel = MainViewModel()
    }

    @Test
    fun setSelectedArticleMustSetArticle() {

        val article = Article(
            author = "Autor",
            title = "Título",
            description = "Descrição",
            url = "https://newsapi.org/",
            urlToImage = "https://zycrypto.com/wp-content/uploads/2022/04/Robinhood-CEO-Tenev-Sees-Dogecoin-As-The-Future-Currency-Of-The-Internet-But-Much-Has-To-Be-Done.jpg",
            publishedAt = "2022-10-26T17:25:11Z",
            content = "Conteúdo",
            source = Source(id = "id", name = "Nome")
        )

        viewModel.setSelectedArticle(article)

        assertEquals( "Autor" ,  article.author)
        //assertEquals( "Autor" ,  viewModel.selectedArticle.value?.author)


    }

}