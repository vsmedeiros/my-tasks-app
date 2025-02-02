package com.example.mytasks.ui

sealed interface UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent
    data object NavigateUp : UiEvent
    data class Navigate<T : Any>(val route: T) : UiEvent
}