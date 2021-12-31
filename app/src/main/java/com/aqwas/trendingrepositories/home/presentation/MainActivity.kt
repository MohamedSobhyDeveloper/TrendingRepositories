package com.aqwas.trendingrepositories.home.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.aqwas.trendingrepositories.R
import com.aqwas.trendingrepositories.core.presentation.base.BaseActivity
import com.aqwas.trendingrepositories.core.presentation.extensions.showGenericAlertDialog
import com.aqwas.trendingrepositories.core.presentation.extensions.showToast
import com.aqwas.trendingrepositories.databinding.ActivityMainBinding
import com.aqwas.trendingrepositories.home.data.responseremote.ModelTrendingRepositoriesRemote
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

        setUpTrendingRepository()
        addListenersOnViews()
        observeStateFlow()
        viewModel.getTrendingRepositoryList()

    }

    private fun setUpTrendingRepository() {
        binding.rvTrendingRepository.adapter = adapter

    }

    private fun addListenersOnViews() {

    }

    private fun observeStateFlow() {
        viewModel.TrendingRepositoryState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: RepositoryListState) {
        when (state) {
            is RepositoryListState.Init -> Unit
            is RepositoryListState.ErrorResponse -> handleErrorLogin(
                state.errorCode,
                state.errorMessage
            )
            is RepositoryListState.SuccessResponse -> handleSuccess(state.repositoryListResponseRemote)
            is RepositoryListState.ShowToast -> showToast(
                state.message,
                state.isConnectionError
            )
            is RepositoryListState.IsLoading -> handleLoading(state.isLoading)
        }

    }

    private fun handleSuccess(list: List<ModelTrendingRepositoriesRemote>?) {
        adapter.submitList(list)
    }


    private fun handleErrorLogin(errorCode: Int, errorMessage: String) {
        showGenericAlertDialog(errorMessage)
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            showProgress()
        } else {
            hidProgress()
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
            return true
        } else if (itemId == R.id.sortByStar) {

            return true
        }
        return super.onOptionsItemSelected(item)
    }

}