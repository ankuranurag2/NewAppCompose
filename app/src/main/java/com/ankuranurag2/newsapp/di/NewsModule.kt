package com.ankuranurag2.newsapp.di

import android.content.Context
import androidx.room.Room
import com.ankuranurag2.newsapp.data.NewsApiInterface
import com.ankuranurag2.newsapp.data.NewsRepositoryImpl
import com.ankuranurag2.newsapp.data.local.NewsDB
import com.ankuranurag2.newsapp.domain.models.repos.INewsRepository
import com.ankuranurag2.newsapp.domain.usecases.FetchTopNewsUseCaseImpl
import com.ankuranurag2.newsapp.domain.usecases.IFetchTopNewsUseCase
import com.ankuranurag2.newsapp.utils.AppConstants
import com.ankuranurag2.newsapp.utils.AppConstants.CONNECTION_TIMEOUT
import com.ankuranurag2.newsapp.utils.AppConstants.READ_TIMEOUT
import com.ankuranurag2.newsapp.utils.AppConstants.WRITE_TIMEOUT
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NewsModule {
    companion object {
        @Provides
        @Singleton
        fun provideRetrofit(): Retrofit {
            val builder = OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)


            return Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .client(builder.build())
                .build()
        }

        @Provides
        @Singleton
        fun provideApiInterface(retrofit: Retrofit): NewsApiInterface {
            return retrofit.create(NewsApiInterface::class.java)
        }

        @Provides
        @Singleton
        fun providesDB(@ApplicationContext context: Context): NewsDB {
            return Room.databaseBuilder(
                context,
                NewsDB::class.java, "newsdb"
            ).build()
        }
    }

    @Binds
    @Singleton
    abstract fun provideFetchTopNewsRepo(newsRepositoryImpl: NewsRepositoryImpl): INewsRepository

    @Binds
    @Singleton
    abstract fun provideFetchTopNewsUseCase(fetchTopNewsUseCaseImpl: FetchTopNewsUseCaseImpl): IFetchTopNewsUseCase
}