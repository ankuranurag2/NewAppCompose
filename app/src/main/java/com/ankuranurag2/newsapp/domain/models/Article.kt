package com.ankuranurag2.newsapp.domain.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "newsTable")
data class Article(
    @PrimaryKey
    @SerializedName("url")
    val url: String,
    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?
)