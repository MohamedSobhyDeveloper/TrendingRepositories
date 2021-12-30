package com.aqwas.trendingrepositories.home.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.aqwas.trendingrepositories.core.presentation.base.BaseActivity
import com.aqwas.trendingrepositories.core.presentation.extensions.showGenericAlertDialog
import com.aqwas.trendingrepositories.core.presentation.extensions.showToast
import com.aqwas.trendingrepositories.databinding.ActivityMainBinding
import com.aqwas.trendingrepositories.home.data.responseremote.ModelTrendingRepositoriesRemote
import com.aqwas.trendingrepositories.home.presentation.viewmodel.RepositoryListState
import com.aqwas.trendingrepositories.home.presentation.viewmodel.TrendingListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel by viewModels<TrendingListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
        addListenersOnViews()
        observeStateFlow()
        viewModel.getTrendingRepositoryList()

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

    private fun addListenersOnViews() {

    }

    private fun initialize() {

    }
}