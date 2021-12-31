package com.aqwas.trendingrepositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.aqwas.trendingrepositories.home.data.datasource.TrendingService
import com.aqwas.trendingrepositories.home.data.repositoryImp.TrendingRepositoryImpl
import com.aqwas.trendingrepositories.home.domain.interactor.TrendingUseCase
import com.aqwas.trendingrepositories.home.domain.repository.TrendingRepository
import com.aqwas.trendingrepositories.home.presentation.viewmodel.RepositoryListState
import com.aqwas.trendingrepositories.home.presentation.viewmodel.TrendingListViewModel
import kotlinx.coroutines.flow.collect
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(MockitoJUnitRunner::class)
class TrendingListingViewModel {
    lateinit var SUT: TrendingListViewModel

    @Mock
    lateinit var repository: TrendingRepository

    @Mock
    lateinit var hasReachedMaxObserver: Observer<Boolean>

    @Rule
    @JvmField
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

}