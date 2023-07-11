package com.ankuranurag2.newsapp.data

import com.ankuranurag2.newsapp.utils.RestClientResult
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsDataSource @Inject constructor(
    private val apiInterface: NewsApiInterface
) {

    private suspend fun <T> getResult(
        apiCall: suspend () -> Response<T>,
    ): RestClientResult<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null)
                    RestClientResult.success(body)
                else
                    RestClientResult.error(response.message())
            } else
                RestClientResult.error(response.message())
        } catch (e: Exception) {
            RestClientResult.error(e.message ?: "Something went wrong")
        }
    }

    suspend fun fetchTopNews() = getResult {
        apiInterface.fetchTopNews()
    }
}