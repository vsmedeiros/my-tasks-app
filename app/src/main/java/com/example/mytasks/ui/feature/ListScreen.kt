package com.example.mytasks.ui.feature

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mytasks.domain.Todo
import com.example.mytasks.domain.todo1
import com.example.mytasks.domain.todo2
import com.example.mytasks.domain.todo3
import com.example.mytasks.ui.components.TodoItem
import com.example.mytasks.ui.theme.MyTasksTheme

@Composable
fun ListScreen() {

}

@Composable
fun ListContent(
    todos: List<Todo>
) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { }) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .consumeWindowInsets(paddingValues),
            contentPadding = PaddingValues(16.dp),
        ) {
            itemsIndexed(todos) { index, todo ->
                TodoItem(
                    todo = todo,
                    onCompletedChange = {},
                    onItemClick = {},
                    onDeleteClick = {}
                )
                if (index < todos.lastIndex) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun ListContentPreview() {
    MyTasksTheme {
        ListContent(
            todos = listOf(
                todo1,
                todo2,
                todo3
            )
        )
    }
}