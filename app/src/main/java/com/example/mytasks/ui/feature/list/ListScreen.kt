package com.example.mytasks.ui.feature.list


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mytasks.data.TodoDatabaseProvider
import com.example.mytasks.data.TodoRepositoryImpl
import com.example.mytasks.domain.Todo
import com.example.mytasks.domain.todo1
import com.example.mytasks.domain.todo2
import com.example.mytasks.domain.todo3
import com.example.mytasks.navigation.AddEditRoute
import com.example.mytasks.ui.UiEvent
import com.example.mytasks.ui.components.TaskComponent
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
    val pendingTasks = todos.filter { !it.isCompleted }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.padding(24.dp, 36.dp)) {
                Text(
                    text = "MyTask!",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text =
                    if (pendingTasks.isNotEmpty()) {
                        "Clique no '+' para adicionar uma nova tarefa!\n\n" +
                                "${pendingTasks.size} tarefa(s) pendente(s)"
                    } else {
                        "Toda(s) as tarefa(s) concluída(s)!"
                    },

                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Gray
                )
            }
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(ListEvent.AddEdit(null)) },
                shape = CircleShape,
                containerColor = Color.Black,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        },

        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(todos) { index, todo ->
                TaskComponent(
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
                todo3,
            ),
            onEvent = {},
        )
    }
}
