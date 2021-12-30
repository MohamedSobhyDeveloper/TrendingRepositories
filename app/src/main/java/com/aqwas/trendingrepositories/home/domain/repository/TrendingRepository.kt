package com.aqwas.trendingrepositories.home.domain.repository

import com.aqwas.trendingrepositories.core.presentation.base.BaseResult
import com.aqwas.trendingrepositories.home.data.responseremote.ModelTrendingRepositoriesRemote
import kotlinx.coroutines.flow.Flow

interface TrendingRepository {
    suspend fun getTrendingRepositories(): Flow<BaseResult<List<ModelTrendingRepositoriesRemote>>>
}