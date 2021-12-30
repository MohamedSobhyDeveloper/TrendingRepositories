package com.aqwas.trendingrepositories.home.data.repositoryImp

import com.aqwas.trendingrepositories.home.data.datasource.TrendingService
import com.aqwas.trendingrepositories.home.domain.repository.TrendingRepository
import javax.inject.Inject

class TrendingRepositoryImpl @Inject constructor(private val trendingService: TrendingService) :
    TrendingRepository