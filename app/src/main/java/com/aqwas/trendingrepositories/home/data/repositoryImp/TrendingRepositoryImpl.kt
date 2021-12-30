package com.aqwas.trendingrepositories.home.data.repositoryImp

import com.aqwas.trendingrepositories.core.presentation.base.BaseResult
import com.aqwas.trendingrepositories.home.data.datasource.TrendingService
import com.aqwas.trendingrepositories.home.data.responseremote.ModelTrendingRepositoriesRemote
import com.aqwas.trendingrepositories.home.domain.repository.TrendingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TrendingRepositoryImpl @Inject constructor(private val trendingService: TrendingService) :
    TrendingRepository {
    override suspend fun getTrendingRepositories(): Flow<BaseResult<List<ModelTrendingRepositoriesRemote>>> {
        return flow {
            val response = trendingService.getTrendingRepositories()
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))

            } else {
                emit(BaseResult.ErrorState(response.code(),response.message()))
            }
        }
    }
}