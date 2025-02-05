package com.example.mytasks.ui.feature.addEdit

sealed interface AddEditEvent {
    data class TitleChanged(val title: String) : AddEditEvent
    data class DescriptionChanged(val description: String) : AddEditEvent
    data class StartTimeChanged(val startTime: String) : AddEditEvent
    data class EndTimeChanged(val endTime: String) : AddEditEvent
    data object Save : AddEditEvent
}