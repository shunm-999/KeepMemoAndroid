package com.example.keepmemo.ui.editkeep

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.keepmemo.domain.AddKeepUseCase
import com.example.keepmemo.model.Keep
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class AddOrEditKeepUiState(
    val targetKeep: Keep = Keep.EMPTY,
    val title: String = "",
    val body: String = "",
    val editTime: Long = System.currentTimeMillis()
)

class AddOrEditViewModel @AssistedInject constructor(
    @Assisted("targetKeepId") private val targetKeepId: Long,
    @Assisted("editTime") editTime: Long = System.currentTimeMillis(),
    private val addKeepUseCase: AddKeepUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddOrEditKeepUiState())
    val uiState: StateFlow<AddOrEditKeepUiState> = _uiState.asStateFlow()

    private val _title = MutableStateFlow("")
    private val _body = MutableStateFlow("")
    private val _editTime = MutableStateFlow(editTime)

    init {
        viewModelScope.launch {
            val targetKeep = Keep.EMPTY // TODO
            fetchUiState(targetKeep)
        }
    }

    private suspend fun fetchUiState(targetKeep: Keep) {
        combine(
            _title,
            _body,
            _editTime
        ) { title, body, editTime ->
            AddOrEditKeepUiState(
                targetKeep = targetKeep,
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

    fun saveKeep() {
        val keep = Keep(
            id = targetKeepId,
            title = _title.value,
            body = _body.value
        )
        addKeepUseCase.invokeAddKeep(keep)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("targetKeepId") targetKeepId: Long,
            @Assisted("editTime") editTime: Long
        ): AddOrEditViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            targetKeepId: Long,
            editTime: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return assistedFactory.create(
                    targetKeepId = targetKeepId,
                    editTime = editTime
                ) as T
            }
        }
    }
}
