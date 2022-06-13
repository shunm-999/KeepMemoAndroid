package com.example.keepmemo.ui.screens.alarm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.keepmemo.domain.AlarmUseCase
import com.example.keepmemo.model.Alarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AlarmUiState(
    val alarmList: List<Alarm> = emptyList()
)

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmUseCase: AlarmUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AlarmUiState())
    val uiState: StateFlow<AlarmUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            alarmUseCase.invokeAlarmList().collect {
                _uiState.value = AlarmUiState(it)
            }
        }
    }

    suspend fun enableAlarm(id: Long) {
        alarmUseCase.invokeEnableAlarm(id)
    }

    suspend fun disableAlarm(id: Long) {
        alarmUseCase.invokeDisableAlarm(id)
    }

    fun setAlarmClock(context: Context, id: Long) {
        alarmUseCase.setAlarmClock(context)
    }
}
