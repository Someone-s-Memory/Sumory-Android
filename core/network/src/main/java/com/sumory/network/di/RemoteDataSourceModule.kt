package com.sumory.network.di

import com.sumory.network.datasource.auth.AuthDataSource
import com.sumory.network.datasource.auth.AuthDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindAuthDataSource(
        authDatasourceImpl: AuthDataSourceImpl
    ): AuthDataSource
}