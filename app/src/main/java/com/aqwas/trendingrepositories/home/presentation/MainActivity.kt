package com.aqwas.trendingrepositories.home.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.aqwas.trendingrepositories.R
import com.aqwas.trendingrepositories.core.presentation.base.BaseActivity
import com.aqwas.trendingrepositories.databinding.ActivityMainBinding
import com.aqwas.trendingrepositories.home.data.responseremote.ModelTrendingRepositoriesRemote
import com.aqwas.trendingrepositories.home.domain.annotation.SortStatus
import com.aqwas.trendingrepositories.home.presentation.adapter.AdapterTrendingRepository
import com.aqwas.trendingrepositories.home.presentation.viewmodel.RepositoryListState
import com.aqwas.trendingrepositories.home.presentation.viewmodel.TrendingListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val adapter: AdapterTrendingRepository by lazy {
        AdapterTrendingRepository()
    }
    private val viewModel by viewModels<TrendingListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
        setUpTrendingRepository()
        addListenersOnViews()
        observeStateFlow(SortStatus.SORT_DEFAULT)
        if (viewModel.isScreenLoaded.not()) {
            viewModel.getTrendingRepositoryList()
        }
    }

    private fun initialize() {
        setSupportActionBar(binding.trendingRepositoryToolbar)
    }

    private fun setUpTrendingRepository() {
        binding.rvTrendingRepository.adapter = adapter
    }

    private fun addListenersOnViews() {
        binding.swipeToRefresh.setOnRefreshListener {
            observeStateFlow(SortStatus.SORT_DEFAULT)
            binding.swipeToRefresh.isRefreshing = false
        }

        binding.retryBtn.setOnClickListener {
            binding.layoutError.isVisible=false
            viewModel.getTrendingRepositoryList()
        }
    }

    private fun observeStateFlow(sortStatus: Int) {
        viewModel.TrendingRepositoryState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state, sortStatus) }
            .launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: RepositoryListState, sortStatus: Int) {
        when (state) {
            is RepositoryListState.Init -> Unit
            is RepositoryListState.ErrorResponse -> handleError(
                state.errorCode,
                state.errorMessage
            )
            is RepositoryListState.SuccessResponse -> handleSuccess(
                state.repositoryListResponseRemote,
                sortStatus
            )
            is RepositoryListState.ShowException -> handleException(
                state.message,
                state.isConnectionError
            )
            is RepositoryListState.IsLoading -> handleLoading(state.isLoading)
        }

    }

    private fun handleSuccess(list: List<ModelTrendingRepositoriesRemote>?, sortStatus: Int) {
        binding.swipeToRefresh.isVisible = true
        binding.layoutError.isVisible = false
        binding.layoutShimmer.isVisible = false
        binding.layoutShimmer.stopShimmer()
        when (sortStatus) {
            SortStatus.SORT_DEFAULT -> adapter.submitList(list)
            SortStatus.SORT_NAME -> {
                val listSortedWithName =
                    list?.sortedWith(compareBy { it.name })
                adapter.submitList(listSortedWithName)
            }
            SortStatus.SORT_STAR -> {
                val listSortedWithStart =
                    list?.sortedWith(compareBy { it.stars })
                adapter.submitList(listSortedWithStart)
            }
        }

        viewModel.isScreenLoaded = true

    }

    private fun handleError(errorCode: Int, errorMessage: String) {
        binding.swipeToRefresh.isVisible = false
        binding.layoutError.isVisible = true
    }

    private fun handleException(message: String, connectionError: Boolean) {
        binding.swipeToRefresh.isVisible = false
        binding.layoutError.isVisible = true
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.layoutShimmer.isVisible = true
            binding.layoutShimmer.startShimmer()
        } else {
            binding.layoutShimmer.isVisible = false
            binding.layoutShimmer.stopShimmer()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.sort_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.sortByName) {
            observeStateFlow(SortStatus.SORT_NAME)
            return true
        } else if (itemId == R.id.sortByStar) {
            observeStateFlow(SortStatus.SORT_STAR)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}