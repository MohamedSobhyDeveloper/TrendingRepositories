package com.aqwas.trendingrepositories.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aqwas.trendingrepositories.core.presentation.base.BaseResult
import com.aqwas.trendingrepositories.home.data.responseremote.ModelTrendingRepositoriesRemote
import com.aqwas.trendingrepositories.home.domain.interactor.TrendingUseCase
import com.aqwas.trendingrepositories.home.presentation.viewmodel.RepositoryListState
import com.aqwas.trendingrepositories.home.presentation.viewmodel.TrendingListViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi

class TrendingListingViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var usecase: TrendingUseCase

    private lateinit var trendingViewModel: TrendingListViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        trendingViewModel = TrendingListViewModel(usecase)

    }

    @Test
    fun test_fetchTrendingList() = runBlocking {
        val retroResponse = ArrayList<ModelTrendingRepositoriesRemote>()
        val response = BaseResult.DataState(retroResponse)
        val channel = Channel<BaseResult<List<ModelTrendingRepositoriesRemote>>>()
        val flow = channel.consumeAsFlow()
        Mockito.`when`(usecase.execute()).thenReturn(flow)

        launch {
            channel.send(response)
        }
        trendingViewModel.getTrendingRepositoryList()
        assertNotNull(trendingViewModel.TrendingRepositoryState.value)
        assertEquals(
            RepositoryListState.SuccessResponse(retroResponse),
            trendingViewModel.TrendingRepositoryState.value
        )
    }
}