package com.ankuranurag2.newsapp.domain.usecases

import com.ankuranurag2.newsapp.domain.models.NewsApiResponse
import com.ankuranurag2.newsapp.utils.RestClientResult
import kotlinx.coroutines.flow.Flow

interface IFetchTopNewsUseCase {

    suspend fun fetchTopNews(): Flow<RestClientResult<NewsApiResponse>>
}