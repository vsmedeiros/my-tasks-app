package com.example.mytasks.ui.feature.addEdit

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytasks.data.TodoRepository
import com.example.mytasks.ui.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


class AddEditViewModel(
    private val id: Long? = null,
    private val repository: TodoRepository,
) : ViewModel() {

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf<String?>(null)
        private set

    var startTime by mutableStateOf<String?>(null)
        private set

    var endTime by mutableStateOf<String?>(null)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        id?.let {
            viewModelScope.launch {
                val todo = repository.getBy(it)
                title = todo?.title ?: ""
                description = todo?.description
                startTime = todo?.startTime
                endTime = todo?.endTime
            }
        }
    }

    fun onEvent(event: AddEditEvent) {
        when (event) {
            is AddEditEvent.TitleChanged -> {
                title = event.title
            }

            is AddEditEvent.DescriptionChanged -> {
                description = event.description
            }

            is AddEditEvent.StartTimeChanged -> {
                startTime = event.startTime
            }

            is AddEditEvent.EndTimeChanged -> {
                endTime = event.endTime
            }

            is AddEditEvent.Save -> {
                saveTodo()
            }
        }
    }

    private fun saveTodo() {
        viewModelScope.launch {
            if (title.isBlank()) {
                _uiEvent.send(
                    UiEvent.ShowSnackbar(
                        message = "O título não pode ficar vazio"
                    )
                )
                return@launch
            }

            if (startTime.isNullOrBlank()) {
                _uiEvent.send(
                    UiEvent.ShowSnackbar(
                        message = "O horário de início não pode ficar vazio"
                    )
                )
                return@launch
            }

            if (endTime.isNullOrBlank()) {
                _uiEvent.send(
                    UiEvent.ShowSnackbar(
                        message = "O horário de término não pode ficar vazio"
                    )
                )
                return@launch
            }

            repository.insert(title, description, id, startTime, endTime)
            _uiEvent.send(UiEvent.NavigateBack)
        }
    }
}
