package com.example.keepmemo.feature.home

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.keepmemo.core.common.result.Result
import com.example.keepmemo.core.common.util.combine
import com.example.keepmemo.core.domain.MemoUseCase
import com.example.keepmemo.core.model.data.Memo
import com.example.keepmemo.core.model.data.UiMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class HomeListPane {
    One,
    Two
}

data class HomeUiState(
    val homeListPane: HomeListPane = HomeListPane.One,
    val memoList: List<Memo> = emptyList(),
    val selectedMemoIdList: Set<Long> = emptySet(),
    val isLoading: Boolean = false,
    val fullScreenMemo: Memo? = null,
    val uiMessages: List<UiMessage> = emptyList()
)

sealed interface HomeUiEvent {
    object NavigateToAddKeep : HomeUiEvent
    data class NavigateToFullScreen(val memo: Memo) : HomeUiEvent
    data class UpdateFullScreenMemoTitle(val title: String) : HomeUiEvent
    data class UpdateFullScreenMemoBody(val body: String) : HomeUiEvent
    object NavigateToMemoList : HomeUiEvent
    data class HomeListPageChange(val homeListPane: HomeListPane) : HomeUiEvent
    data class AddToSelectedIdList(val keepId: Long) : HomeUiEvent
    data class RemoveFromSelectedIdList(val keepId: Long) : HomeUiEvent
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val memoUseCase: MemoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _homeListPane = MutableStateFlow(HomeListPane.One)

    private val _selectedMemoIdList = MutableStateFlow(mutableSetOf<Long>())

    private val _isLoading = MutableStateFlow(false)

    private val _fullScreenMemo = MutableStateFlow<Memo?>(null)

    private val _uiMessages = MutableStateFlow(emptyList<UiMessage>())

    init {
        viewModelScope.launch {
            combine(
                memoUseCase.invokeMemoList(),
                _selectedMemoIdList,
                _homeListPane,
                _isLoading,
                _fullScreenMemo,
                _uiMessages
            ) { memoList,
                selectedMemoIdList,
                homeListPane,
                isLoading,
                fullScreenMemo,
                uiMessages ->
                HomeUiState(
                    memoList = when (memoList) {
                        is Result.Success -> memoList.data
                        is Result.Error -> emptyList()
                    },
                    selectedMemoIdList = selectedMemoIdList,
                    homeListPane = homeListPane,
                    isLoading = isLoading,
                    fullScreenMemo = fullScreenMemo,
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

    fun toFullScreen(memo: Memo) {
        _fullScreenMemo.value = memo
    }

    fun updateFullScreenMemoTitle(title: String) {
        _fullScreenMemo.update { memo ->
            if (memo == null) {
                return@update null
            }
            memo.copy(
                keep = memo.keep.copy(
                    title = title
                )
            )
        }
    }

    fun updateFullScreenMemoBody(body: String) {
        _fullScreenMemo.update { memo ->
            if (memo == null) {
                return@update null
            }
            memo.copy(
                keep = memo.keep.copy(
                    body = body
                )
            )
        }
    }

    fun toMemoList() {
        _fullScreenMemo.value = null
    }

    suspend fun saveKeep() {
        _fullScreenMemo.value?.let { memo ->
            memoUseCase.invokeUpdateMemo(
                memoId = memo.id,
                title = memo.keep.title,
                body = memo.keep.body
            )
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

    fun dismissMessage(id: Long) {
        _uiMessages.update {
            it.filterNot { uiMessage ->
                uiMessage.id == id
            }
        }
    }
}
