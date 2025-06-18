package com.sumory.network.di

import android.util.Log
import com.sumory.network.BuildConfig
import com.sumory.network.api.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message -> Log.v("Http", message) }
            .setLevel(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            )

    @Provides
    @Singleton
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideAuthAPI(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)
}
