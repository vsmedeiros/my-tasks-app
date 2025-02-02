package com.example.mytasks.ui.feature.addEdit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytasks.data.TodoEntity
import com.example.mytasks.data.TodoRepository
import com.example.mytasks.ui.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AddEditViewModel (
    private val repository: TodoRepository,
) : ViewModel()  {

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf<String?>(null)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: addEditEvent) {
        when (event) {
            is addEditEvent.TitleChanged -> {
                title = event.title
            }
            is addEditEvent.DescriptionChanged -> {
                description = event.description
            }
            is addEditEvent.Save -> {
                saveTodo()

            }

        }


    }

    private fun saveTodo() {
                viewModelScope.launch {
                    if (title.isBlank()) {
                        _uiEvent.send(UiEvent.ShowSnackbar(
                            message = "O título não pode ficar vazio"
                        ))
                        return@launch
                        repository.insert(TodoEntity(title = title, description = description))
                    }
        }

    }
}