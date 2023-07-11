package com.ankuranurag2.newsapp.data.local

import androidx.room.*
import com.ankuranurag2.newsapp.domain.models.Article

@Dao
interface NewsDao {
    @Query("Select * from newsTable")
    suspend fun getAllNews(): List<Article>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllNews(articleList: List<Article>)
}