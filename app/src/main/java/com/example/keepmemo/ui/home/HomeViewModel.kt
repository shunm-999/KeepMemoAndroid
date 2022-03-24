package com.example.keepmemo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.keepmemo.data.Result
import com.example.keepmemo.domain.KeepMemoListUseCase
import com.example.keepmemo.model.Keep
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val keepMemoList: List<Keep> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val keepMemoListUseCase: KeepMemoListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _memoList = keepMemoListUseCase.invokeKeepMemoList()

    private val _isLoading = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            combine(
                _memoList,
                _isLoading
            ) { memoList, loading ->
                HomeUiState(
                    keepMemoList = when (memoList) {
                        is Result.Success -> memoList.data
                        is Result.Error -> emptyList()
                    },
                    isLoading = loading
                )
            }.collect {
                _uiState.value = it
            }
        }
    }
}
