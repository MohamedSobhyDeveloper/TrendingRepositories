package com.aqwas.trendingrepositories.home.presentation.di

import com.aqwas.trendingrepositories.core.data.module.NetworkModule
import com.aqwas.trendingrepositories.home.data.datasource.TrendingService
import com.aqwas.trendingrepositories.home.data.repositoryImp.TrendingRepositoryImpl
import com.aqwas.trendingrepositories.home.domain.repository.TrendingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class TrendingModule {

    @Singleton
    @Provides
    fun provideLoginApi(retrofit: Retrofit): TrendingService {
        return retrofit.create(TrendingService::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(trendingService: TrendingService): TrendingRepository {
        return TrendingRepositoryImpl(trendingService)
    }

}