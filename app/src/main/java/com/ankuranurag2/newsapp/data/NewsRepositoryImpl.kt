package com.ankuranurag2.newsapp.data

import com.ankuranurag2.newsapp.data.local.NewsDB
import com.ankuranurag2.newsapp.domain.models.NewsApiResponse
import com.ankuranurag2.newsapp.domain.models.repos.INewsRepository
import com.ankuranurag2.newsapp.utils.RestClientResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val newsDataSource: NewsDataSource,
    private val database: NewsDB
) : INewsRepository {

    suspend fun <T> getFlowResult(call: suspend () -> RestClientResult<T>): Flow<RestClientResult<T>> =
        flow {
            emit(RestClientResult.loading())
            val savedList = database.newsDao().getAllNews()
            if (savedList.isEmpty()) {
                val result = call.invoke()
                emit(result)
                (result.data as? NewsApiResponse)?.articles?.let {
                    database.newsDao().insertAllNews(it)
                }
            } else
                emit(RestClientResult.success(NewsApiResponse(articles = savedList)) as RestClientResult<T>)
        }

    override suspend fun fetchTopNews() = getFlowResult {
        newsDataSource.fetchTopNews()
    }
}