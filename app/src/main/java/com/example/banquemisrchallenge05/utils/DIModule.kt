package com.example.banquemisrchallenge05.utils

import android.app.Application
import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.banquemisrchallenge05.data.localDS.MovieDataBase
import com.example.banquemisrchallenge05.data.network.ApiService
import com.example.banquemisrchallenge05.data.remoteDS.RemoteDS
import com.example.banquemisrchallenge05.data.remoteDS.RemoteDSImpl
import com.example.banquemisrchallenge05.data.repository.NowPlayingMovieMediator
import com.example.banquemisrchallenge05.data.repository.PopularMediator
import com.example.banquemisrchallenge05.data.repository.Repository
import com.example.banquemisrchallenge05.data.repository.RepositoryImpl
import com.example.banquemisrchallenge05.data.repository.UpComingMediator
import com.example.banquemisrchallenge05.model.MovieResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    @Named("apiServiceWithNoCache")
    fun provideApiService(): ApiService {
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
    fun provideMoviePager(@Named("popular") movieDB: MovieDataBase,@Named("apiServiceWithNoCache") movieAPI: ApiService): Pager<Int, MovieResponse> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = PopularMediator(
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
    fun provideUpComingMoviePager( @Named("upcoming") movieDB: MovieDataBase, @Named("apiServiceWithNoCache")movieAPI: ApiService): Pager<Int, MovieResponse> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = UpComingMediator(
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
    fun provideNowPlayingMoviePager(@Named("nowPlaying") movieDB: MovieDataBase, @Named("apiServiceWithNoCache")movieAPI: ApiService): Pager<Int, MovieResponse> {
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



    @Provides
    @Singleton
    @Named("ApiServiceWithCache")
    fun provideApiServiceWithCache(application: Application): ApiService {
        val cacheSize = 100 * 1024 * 1024
        val cacheDir = File(application.cacheDir, "httpCache")

        // Create the cache
        val cache = Cache(cacheDir, cacheSize.toLong())

        val okHttpClient = OkHttpClient.Builder()
            .cache(cache) // Set the cache
            .addInterceptor { chain ->
                var request = chain.request()
                request = request.newBuilder()
                    .addHeader("Authorization", Constants.API_KEY)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(Interceptor { chain ->
                var request = chain.request()
                // Use cache if the device is offline
                request = request.newBuilder()
                    .header("Cache-Control", "public, max-age=300") // Cache for 5 minutes
                    .build()
                chain.proceed(request)
            })
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDS( @Named("ApiServiceWithCache") apiService: ApiService): RemoteDS {
        return RemoteDSImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideRepository(remoteDS: RemoteDS): Repository {
        return RepositoryImpl(remoteDS)
    }
}
