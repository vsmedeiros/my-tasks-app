package com.example.mytasks.ui.feature.list

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mytasks.data.TodoDatabaseProvider
import com.example.mytasks.data.TodoRepositoryImpl
import com.example.mytasks.domain.Todo
import com.example.mytasks.domain.todo1
import com.example.mytasks.domain.todo2
import com.example.mytasks.domain.todo3
import com.example.mytasks.navigation.AddEditRoute
import com.example.mytasks.ui.UiEvent
import com.example.mytasks.ui.components.TodoItem
import com.example.mytasks.ui.feature.addEdit.AddEditViewModel
import com.example.mytasks.ui.theme.MyTasksTheme

@Composable
fun ListScreen(
    navigateToAddEditScreen: (id: Long?) -> Unit,
) {
    val context = LocalContext.current.applicationContext
    val database = TodoDatabaseProvider.provide(context)
    val repository = TodoRepositoryImpl(
        dao = database.dao
    )
    val viewModel = viewModel<ListViewModel> {
        ListViewModel(repository = repository)

    }
    val todos = viewModel.todos.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate<*> -> {
                    when (uiEvent.route) {
                        is AddEditRoute -> {
                            navigateToAddEditScreen(uiEvent.route.id)
                        }
                    }

                }

                UiEvent.NavigateBack -> {}
                is UiEvent.ShowSnackbar -> {}
            }
        }
    }
    ListContent(
        todos = todos.value,
        onEvent = viewModel::onEvent
    )
}


@Composable
fun ListContent(
    todos: List<Todo>,
    onEvent: (ListEvent) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(ListEvent.AddEdit(null))
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
            itemsIndexed(todos) { index, todo ->
                TodoItem(
                    todo = todo,
                    onCompletedChange = {
                        onEvent(ListEvent.CompleteChanged(todo.id, it))
                    },
                    onItemClicked = {
                        onEvent(ListEvent.AddEdit(todo.id))
                    },
                    onDeleteClicked = {
                        onEvent(ListEvent.Delete(todo.id))
                    }
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
            onEvent = {},
        )
    }
}
