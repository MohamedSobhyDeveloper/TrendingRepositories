package com.aqwas.trendingrepositories.home.domain.interactor

import com.aqwas.trendingrepositories.core.presentation.base.BaseResult
import com.aqwas.trendingrepositories.home.data.responseremote.ModelTrendingRepositoriesRemote
import com.aqwas.trendingrepositories.home.domain.repository.TrendingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrendingUseCase @Inject constructor(private val trendingRepository: TrendingRepository) {
    suspend fun execute(): Flow<BaseResult<List<ModelTrendingRepositoriesRemote>>> {
        return trendingRepository.getTrendingRepositories()
    }
}