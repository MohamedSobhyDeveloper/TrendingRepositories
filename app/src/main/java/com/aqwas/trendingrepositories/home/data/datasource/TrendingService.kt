package com.aqwas.trendingrepositories.home.data.datasource

import com.aqwas.trendingrepositories.home.data.responseremote.ModelTrendingRepositoriesRemote
import retrofit2.Response
import retrofit2.http.GET

interface TrendingService {
    @GET("repositories")
    suspend fun getTrendingRepositories(): Response<List<ModelTrendingRepositoriesRemote>>
}
