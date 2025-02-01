package com.example.mytasks.ui.feature

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
fun ListScreen(
    navigateToAddEditScreen: (id: Long?) -> Unit,
) {
    ListContent(
        todos = emptyList(),
        onAddItemClick = {
            navigateToAddEditScreen(null)
        }
    )
}


@Composable
fun ListContent(
    todos: List<Todo>,
    onAddItemClick: () -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddItemClick()
                }
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
                .padding(paddingValues), // ✅ Aplicando o padding corretamente
            contentPadding = PaddingValues(16.dp) // ✅ Correção do erro
        ) {
            itemsIndexed(todos) {
                index, it ->
                TodoItem(
                    todo = it,
                    onCompletedChange = {},
                    onItemClicked = {},
                    onDeleteClicked = {}
                )
                if (index < todos.lastIndex) {
                    Spacer(modifier = Modifier.height(8.dp))
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
                todo3,
            ),
            onAddItemClick = {},
        )
    }
}
