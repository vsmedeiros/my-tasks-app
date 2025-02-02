package com.example.mytasks.ui.feature.addEdit

sealed interface addEditEvent {
    data class TitleChanged(val title: String) : addEditEvent
    data class DescriptionChanged(val description: String) : addEditEvent
    data object Save : addEditEvent

}