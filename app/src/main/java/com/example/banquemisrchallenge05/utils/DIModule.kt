package com.example.banquemisrchallenge05.utils

import android.util.Log
import com.example.banquemisrchallenge05.data.network.ApiService
import com.example.banquemisrchallenge05.data.remoteDS.RemoteDSImpl
import com.example.banquemisrchallenge05.data.repository.Repository
import com.example.banquemisrchallenge05.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun ProvideRepository(): Repository {
        return RepositoryImpl(RemoteDSImpl(ProvideApiService()))
    }


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


}
