package com.example.mytasks.domain

import java.util.concurrent.TimeUnit


data class Todo(
    val id: Long,
    val title: String,
    val description: String?,
    val isCompleted: Boolean,
    val startTime: String?,
    val endTime: String?
)

// fake objects

val todo1 = Todo(
    id = 1,
    title = "Todo 1",
    description = "Description for Todo 1",
    isCompleted = false,
    startTime = "05:00 PM",
    endTime = "07:00 PM"
)

val todo2 = Todo(
    id = 2,
    title = "Todo 2",
    description = "Description for Todo 2",
    isCompleted = true,
    startTime = "05:00 PM",
    endTime = "07:00 PM"
)

val todo3 = Todo(
    id = 3,
    title = "Todo 3",
    description = "Description for Todo 3",
    isCompleted = false,
    startTime = "05:00 PM",
    endTime = "07:00 PM"
)

