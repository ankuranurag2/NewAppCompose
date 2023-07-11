package com.ankuranurag2.newsapp.domain.models


import com.google.gson.annotations.SerializedName

data class NewsApiResponse(
    @SerializedName("articles")
    val articles: List<Article>? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("message")
    val message: String? = null
) {
    fun getStatus(): ApiStatus {
        return try {
            return ApiStatus.valueOf(status ?: "")
        } catch (e: Exception) {
            ApiStatus.ERROR
        }
    }
}

enum class ApiStatus(status: String) {
    OK("ok"),
    ERROR("error")
}