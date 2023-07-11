package com.ankuranurag2.newsapp.data

import com.ankuranurag2.newsapp.domain.models.NewsApiResponse
import com.ankuranurag2.newsapp.utils.AppConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiInterface {
    @GET("v2/top-headlines")
    suspend fun fetchTopNews(
        @Query("country") country: String = "in",
        @Query("apiKey") apiKey: String = AppConstants.API_KEY
    ): Response<NewsApiResponse>
}