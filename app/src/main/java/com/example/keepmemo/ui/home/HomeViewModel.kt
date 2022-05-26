package com.example.keepmemo.ui.home

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.keepmemo.data.Result
import com.example.keepmemo.domain.MemoUseCase
import com.example.keepmemo.model.Memo
import com.example.keepmemo.ui.utils.UiMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

enum class HomeListPane {
    One,
    Two
}

data class HomeUiState(
    val homeListPane: HomeListPane = HomeListPane.One,
    val memoList: List<Memo> = emptyList(),
    val selectedMemoIdList: Set<Long> = emptySet(),
    val isLoading: Boolean = false,
    val uiMessages: List<UiMessage> = emptyList()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val memoUseCase: MemoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _homeListPane = MutableStateFlow(HomeListPane.One)

    private val _selectedMemoIdList = MutableStateFlow(mutableSetOf<Long>())

    private val _isLoading = MutableStateFlow(false)

    private val _uiMessages = MutableStateFlow(emptyList<UiMessage>())

    init {
        viewModelScope.launch {
            combine(
                memoUseCase.invokeMemoList(),
                _selectedMemoIdList,
                _homeListPane,
                _isLoading,
                _uiMessages
            ) { memoList, selectedMemoIdList, homeListPane, loading, uiMessages ->
                HomeUiState(
                    memoList = when (memoList) {
                        is Result.Success -> memoList.data
                        is Result.Error -> emptyList()
                    },
                    selectedMemoIdList = selectedMemoIdList,
                    homeListPane = homeListPane,
                    isLoading = loading,
                    uiMessages = uiMessages
                )
            }.collect {
                _uiState.value = it
            }
        }
    }

    fun updateListPane(homeListPane: HomeListPane) {
        _homeListPane.value = homeListPane
    }

    fun addSelectedMemoId(memoId: Long) {
        _selectedMemoIdList.value = mutableSetOf<Long>().apply {
            addAll(_selectedMemoIdList.value)
            add(memoId)
        }
    }

    fun removeSelectedMemoId(memoId: Long) {
        _selectedMemoIdList.value = mutableSetOf<Long>().apply {
            addAll(_selectedMemoIdList.value)
            remove(memoId)
        }
    }

    fun showMessage(@StringRes messageId: Int) {
        _uiMessages.update {
            it + UiMessage(
                id = UUID.randomUUID().mostSignificantBits,
                messageId = messageId
            )
        }
    }

    fun onShowMessage(id: Long) {
        _uiMessages.update {
            it.filterNot { uiMessage ->
                uiMessage.id == id
            }
        }
    }
}
