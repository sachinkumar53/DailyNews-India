package com.sachin.app.dailynews_india.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sachin.app.dailynews_india.api.NewsApiService
import com.sachin.app.dailynews_india.model.Article
import com.sachin.app.dailynews_india.model.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {
    private var articles: MutableLiveData<List<Article>> = MutableLiveData()
    private var totalResults = 0

    fun getArticleLiveData() = articles
    fun getTotalResults() = totalResults

    fun loadNews(
        newsApiService: NewsApiService,
        category: String?,
        page: Int? = null,
        onComplete: (Int) -> Unit
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            newsApiService.getTopHeadlines(category, page).enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    Log.d(TAG, "onResponse: Status ${response.code()}")

                    response.body()?.let {
                        totalResults = it.totalResults
                        articles.postValue(it.articles)
                    }

                    onComplete.invoke(response.code())
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ", t)
                }
            })
        }


    }

    companion object {
        private const val TAG = "NewsViewModel"
    }
}