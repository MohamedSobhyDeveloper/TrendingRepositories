package com.aqwas.trendingrepositories.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aqwas.trendingrepositories.core.presentation.base.BaseResult
import com.aqwas.trendingrepositories.domain.mock
import com.aqwas.trendingrepositories.domain.whenever
import com.aqwas.trendingrepositories.home.data.responseremote.ModelTrendingRepositoriesRemote
import com.aqwas.trendingrepositories.home.domain.interactor.TrendingUseCase
import com.aqwas.trendingrepositories.home.domain.repository.TrendingRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class TrendingListingUseCaseTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val trendingRepository = mock<TrendingRepository>()

    val trendingUseCase by lazy { TrendingUseCase(trendingRepository) }

    @Test
    fun testCryptoListUseCases_getCryptoList_Completed() {
        runBlocking {
            val channel = Channel<BaseResult<List<ModelTrendingRepositoriesRemote>>>()
            val flow = channel.consumeAsFlow()
            whenever(trendingRepository.getTrendingRepositories()).thenReturn(flow)
            trendingUseCase.execute()
        }
    }
}