package com.aqwas.trendingrepositories.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aqwas.trendingrepositories.core.presentation.base.BaseResult
import com.aqwas.trendingrepositories.home.data.responseremote.ModelTrendingRepositoriesRemote
import com.aqwas.trendingrepositories.home.domain.interactor.TrendingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class TrendingListViewModel @Inject constructor(private val trendingUseCase: TrendingUseCase) :
    ViewModel() {
    private val TrendingRepositoryState_ =
        MutableStateFlow<RepositoryListState>(RepositoryListState.Init)
    val TrendingRepositoryState: StateFlow<RepositoryListState> get() = TrendingRepositoryState_

    fun getTrendingRepositoryList() {
        viewModelScope.launch {
            trendingUseCase.execute()
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.message.toString(), exception is UnknownHostException)
                }
                .collect {
                    hideLoading()
                    when (it) {
                        is BaseResult.ErrorState -> TrendingRepositoryState_.value =
                            RepositoryListState.ErrorResponse(it.errorCode, it.errorMessage)
                        is BaseResult.DataState -> {

                            TrendingRepositoryState_.value =
                                it.items.let { it1 ->
                                    RepositoryListState.SuccessResponse(
                                        it1
                                    )
                                }

                        }
                    }
                }
        }
    }

    private fun setLoading() {
        TrendingRepositoryState_.value = RepositoryListState.IsLoading(true)
    }

    private fun hideLoading() {
        TrendingRepositoryState_.value = RepositoryListState.IsLoading(false)
    }

    private fun showToast(message: String, isConnectionError: Boolean) {
        TrendingRepositoryState_.value = RepositoryListState.ShowToast(message, isConnectionError)
    }
}

sealed class RepositoryListState {
    object Init : RepositoryListState()
    data class IsLoading(val isLoading: Boolean) : RepositoryListState()
    data class ShowToast(val message: String, val isConnectionError: Boolean) :
        RepositoryListState()

    data class SuccessResponse(val repositoryListResponseRemote: List<ModelTrendingRepositoriesRemote>?) :
        RepositoryListState()

    data class ErrorResponse(val errorCode: Int, val errorMessage: String) : RepositoryListState()
}