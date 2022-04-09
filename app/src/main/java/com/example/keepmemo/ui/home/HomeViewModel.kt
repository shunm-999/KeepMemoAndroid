package com.example.keepmemo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.keepmemo.data.Result
import com.example.keepmemo.domain.MemoUseCase
import com.example.keepmemo.model.Memo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class HomeListPane {
    One,
    Two
}

data class HomeUiState(
    val homeListPane: HomeListPane = HomeListPane.One,
    val memoList: List<Memo> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val memoUseCase: MemoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _homeListPane = MutableStateFlow(HomeListPane.One)

    private val _isLoading = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            combine(
                memoUseCase.invokeMemoList(),
                _homeListPane,
                _isLoading
            ) { memoList, homeListPane, loading ->
                HomeUiState(
                    memoList = when (memoList) {
                        is Result.Success -> memoList.data
                        is Result.Error -> emptyList()
                    },
                    homeListPane = homeListPane,
                    isLoading = loading
                )
            }.collect {
                _uiState.value = it
            }
        }
    }

    fun updateListPane(homeListPane: HomeListPane) {
        _homeListPane.value = homeListPane
    }
}