package com.ankuranurag2.newsapp.domain.usecases

import com.ankuranurag2.newsapp.domain.models.NewsApiResponse
import com.ankuranurag2.newsapp.domain.models.repos.INewsRepository
import com.ankuranurag2.newsapp.utils.RestClientResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchTopNewsUseCaseImpl @Inject constructor(
    private val repository: INewsRepository
) : IFetchTopNewsUseCase {
    override suspend fun fetchTopNews(): Flow<RestClientResult<NewsApiResponse>> = repository.fetchTopNews()
}