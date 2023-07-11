package com.ankuranurag2.newsapp.domain.models.repos

import com.ankuranurag2.newsapp.domain.models.NewsApiResponse
import com.ankuranurag2.newsapp.utils.RestClientResult
import kotlinx.coroutines.flow.Flow

interface INewsRepository {
    suspend fun fetchTopNews(): Flow<RestClientResult<NewsApiResponse>>
}