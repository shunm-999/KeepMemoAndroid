package com.example.keepmemo.ui.editkeep

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.keepmemo.data.Result
import com.example.keepmemo.domain.MemoUseCase
import com.example.keepmemo.model.Keep
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

sealed interface AddOrEditKeepUiState {
    object Loading : AddOrEditKeepUiState
    data class INITIALIZED(
        val title: String = "",
        val body: String = "",
        val editTime: Long = System.currentTimeMillis()
    ) : AddOrEditKeepUiState
}

class AddOrEditMemoViewModel @AssistedInject constructor(
    @Assisted("targetId") private val targetId: Long,
    @Assisted("editTime") editTime: Long = System.currentTimeMillis(),
    private val memoUseCase: MemoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AddOrEditKeepUiState>(AddOrEditKeepUiState.Loading)
    val uiState: StateFlow<AddOrEditKeepUiState> = _uiState.asStateFlow()

    private val _title = MutableStateFlow("")
    private val _body = MutableStateFlow("")
    private val _editTime = MutableStateFlow(editTime)

    init {
        viewModelScope.launch {
            val targetKeep = if (targetId < 0) {
                Keep.EMPTY
            } else {
                when (val result = memoUseCase.invokeMemo(targetId)) {
                    is Result.Success -> result.data.keep
                    is Result.Error -> Keep.EMPTY
                }
            }

            _title.value = targetKeep.title
            _body.value = targetKeep.body

            fetchUiState()
        }
    }

    private suspend fun fetchUiState() {
        combine(
            _title,
            _body,
            _editTime
        ) { title, body, editTime ->
            AddOrEditKeepUiState.INITIALIZED(
                title = title,
                body = body,
                editTime = editTime
            )
        }.collect {
            _uiState.value = it
        }
    }

    fun updateTitle(title: String) {
        _title.value = title
    }

    fun updateBody(body: String) {
        _body.value = body
    }

    suspend fun saveKeep(): Result<Unit> {
        val title = _title.value
        val body = _body.value

        return if (targetId > 0) {
            memoUseCase.invokeUpdateMemo(
                memoId = targetId,
                title = title,
                body = body
            )
        } else {
            memoUseCase.invokeAddMemo(
                title = title,
                body = body
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("targetId") targetId: Long,
            @Assisted("editTime") editTime: Long
        ): AddOrEditMemoViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            targetId: Long,
            editTime: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return assistedFactory.create(
                    targetId = targetId,
                    editTime = editTime
                ) as T
            }
        }
    }
}
