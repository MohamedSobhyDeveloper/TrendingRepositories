package com.aqwas.trendingrepositories.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aqwas.trendingrepositories.data.MockWebServerBaseTest
import com.aqwas.trendingrepositories.home.data.datasource.TrendingService
import com.aqwas.trendingrepositories.home.data.repositoryImp.TrendingRepositoryImpl
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class TrendingRepositoryTest : MockWebServerBaseTest() {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var trendingRepository: TrendingRepositoryImpl
    private lateinit var apiService: TrendingService

    override fun isMockServerEnabled() = true

    @Before
    fun start() {
        apiService = provideTestApiService()
        trendingRepository = TrendingRepositoryImpl(apiService)
    }

    @Test
    fun getTrendingList() {
        runBlocking {
            mockHttpResponse("json/trending_response_one_item.json", HttpURLConnection.HTTP_OK)
            val apiResponse = trendingRepository.getTrendingRepositories()
            assertNotNull(apiResponse)
            assertEquals(apiResponse.toList().size, 1)
        }
    }

    @Test
    fun givenEmptyList() {
        runBlocking {
            mockHttpResponse("json/trending_response_empty_list.json", HttpURLConnection.HTTP_OK)
            val apiResponse = trendingRepository.getTrendingRepositories()

            assertNotNull(apiResponse)
            assertEquals(apiResponse.toList().size, 0)
        }
    }

}