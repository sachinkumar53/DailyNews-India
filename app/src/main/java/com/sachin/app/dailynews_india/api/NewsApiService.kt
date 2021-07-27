package com.sachin.app.dailynews_india.api

import com.sachin.app.dailynews_india.api.NewsApiClient.API_KEY
import com.sachin.app.dailynews_india.model.NewsResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("category") category: String?,
        @Query("page") page: Int? = null,
        @Query("country") country: String = "in",
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<NewsResponse>


    @GET("everything")
    fun searchAllNews(
        @Query("q") about: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<ResponseBody>

}