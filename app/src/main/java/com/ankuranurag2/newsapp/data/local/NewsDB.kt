package com.ankuranurag2.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ankuranurag2.newsapp.domain.models.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class NewsDB : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}