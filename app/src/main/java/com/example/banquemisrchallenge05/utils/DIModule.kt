package com.example.banquemisrchallenge05.utils

import android.content.Context
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.banquemisrchallenge05.data.localDS.MovieDataBase
import com.example.banquemisrchallenge05.data.network.ApiService
import com.example.banquemisrchallenge05.data.repository.NowPlayingMovieMediator
import com.example.banquemisrchallenge05.data.repository.PopularMovieMediator
import com.example.banquemisrchallenge05.data.repository.UpComingMovieMediator
import com.example.banquemisrchallenge05.model.MovieResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun ProvideApiService(): ApiService {
        Log.i("MovieViewModel", "getPopularMovies: di")

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                var request = chain.request()
                request = request.newBuilder()
                    .addHeader("Authorization", Constants.API_KEY)
                    .build()
                chain.proceed(request)
            }

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL).client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }

    @Provides
    @Singleton
    @Named("popular")
    fun providePopularMovieDatabase(@ApplicationContext context: Context): MovieDataBase {
        return Room.databaseBuilder(
            context,
            MovieDataBase::class.java,
            "popularMovie.db"
        ).build()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    @Named("Popular")
    fun provideMoviePager(@Named("popular") movieDB: MovieDataBase, movieAPI: ApiService): Pager<Int, MovieResponse> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = PopularMovieMediator(
                movieDB = movieDB,
                movieAPI = movieAPI
            ),
            pagingSourceFactory = {
                movieDB.movieDAO.pagingSource()
            }
        )
    }

    @Provides
    @Singleton
    @Named("upcoming")
    fun provideUpComingMovieDatabase(@ApplicationContext context: Context): MovieDataBase {
        return Room.databaseBuilder(
            context,
            MovieDataBase::class.java,
            "upcomingMovie.db"
        ).build()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    @Named("Upcoming")
    fun provideUpComingMoviePager( @Named("upcoming") movieDB: MovieDataBase, movieAPI: ApiService): Pager<Int, MovieResponse> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = UpComingMovieMediator(
                movieDB = movieDB,
                movieAPI = movieAPI
            ),
            pagingSourceFactory = {
                movieDB.movieDAO.pagingSource()
            }
        )
    }


    @Provides
    @Singleton
    @Named("nowPlaying")
    fun provideNowPlayingMovieDatabase(@ApplicationContext context: Context): MovieDataBase {
        return Room.databaseBuilder(
            context,
            MovieDataBase::class.java,
            "nowPlayingMovie.db"
        ).build()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    @Named("NowPlaying")
    fun provideNowPlayingMoviePager(@Named("nowPlaying") movieDB: MovieDataBase, movieAPI: ApiService): Pager<Int, MovieResponse> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = NowPlayingMovieMediator(
                movieDB = movieDB,
                movieAPI = movieAPI
            ),
            pagingSourceFactory = {
                movieDB.movieDAO.pagingSource()
            }
        )
    }

}
