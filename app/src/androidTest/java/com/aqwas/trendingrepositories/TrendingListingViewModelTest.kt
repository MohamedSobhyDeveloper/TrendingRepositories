package com.aqwas.trendingrepositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.aqwas.trendingrepositories.home.data.datasource.TrendingService
import com.aqwas.trendingrepositories.home.data.repositoryImp.TrendingRepositoryImpl
import com.aqwas.trendingrepositories.home.domain.interactor.TrendingUseCase
import com.aqwas.trendingrepositories.home.presentation.viewmodel.RepositoryListState
import com.aqwas.trendingrepositories.home.presentation.viewmodel.TrendingListViewModel
import kotlinx.coroutines.flow.collect
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(AndroidJUnit4ClassRunner::class)
class TrendingListingViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private var viewModel: TrendingListViewModel? = null

    private lateinit var apiService: TrendingService

    lateinit var repositoryRemote: TrendingRepositoryImpl

    @Before
    fun init() {
        apiService = Retrofit.Builder()
            .baseUrl("https://private-anon-e0300a73b5-githubtrendingapi.apiary-mock.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TrendingService::class.java)
        repositoryRemote = TrendingRepositoryImpl(apiService)
        val trendingRemoteUseCase = TrendingUseCase(repositoryRemote)
        viewModel = TrendingListViewModel(trendingRemoteUseCase)
    }

    @Test
    suspend fun testTrendingViewModel() {
        init()
        viewModel?.TrendingRepositoryState?.collect {
            when (it) {
                is RepositoryListState.SuccessResponse -> Assert.assertEquals(
                    it.repositoryListResponseRemote?.size, 4
                )
            }
        }
    }
}