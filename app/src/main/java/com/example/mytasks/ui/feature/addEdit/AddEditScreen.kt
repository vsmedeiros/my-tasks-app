package com.example.mytasks.ui.feature.addEdit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mytasks.data.TodoDatabaseProvider
import com.example.mytasks.data.TodoRepositoryImpl
import com.example.mytasks.ui.UiEvent
import com.example.mytasks.ui.theme.MyTasksTheme

@Composable
fun AddEditScreen(
    id: Long?,
    navigateBack: () -> Unit,

    ) {
    val context = LocalContext.current.applicationContext
    val database = TodoDatabaseProvider.provide(context)
    val repository = TodoRepositoryImpl(
        dao = database.dao
    )
    val viewModel = viewModel<AddEditViewModel> {
        AddEditViewModel(
            id = id,
            repository = repository
        )

    }

    val title = viewModel.title
    val description = viewModel.description
    val startTime = viewModel.startTime
    val endTime = viewModel.endTime

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = uiEvent.message
                    )

                }

                is UiEvent.NavigateBack -> {
                    navigateBack()
                }

                is UiEvent.Navigate<*> -> {
                }
            }
        }
    }

    AddEditContent(
        title = title,
        description = description,
        startTime = startTime,
        endTime = endTime,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun AddEditContent(
    title: String = "",
    description: String? = null,
    startTime: String? = null,
    endTime: String? = null,
    snackbarHostState: SnackbarHostState,
    onEvent: (AddEditEvent) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(shape = CircleShape,
                containerColor = Color.Black,
                contentColor = Color.White,
                onClick = {
                    onEvent(AddEditEvent.Save)
                }) {
                Icon(Icons.Default.Check, contentDescription = "Salvo!")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = {
                    onEvent(
                        AddEditEvent.TitleChanged(it)
                    )

                },
                label = { Text("Título") },
                placeholder = { Text("Digite o título") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = description ?: "",
                onValueChange = {
                    onEvent(
                        AddEditEvent.DescriptionChanged(it)
                    )

                },
                label = { Text("Descrição (opcional)") },
                placeholder = { Text("Digite a descrição") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = startTime ?: "",
                    onValueChange = { onEvent(AddEditEvent.StartTimeChanged(it)) },
                    label = { Text("Hora de início") },
                    placeholder = { Text("HH:MM AM") }
                )

                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = endTime ?: "",
                    onValueChange = { onEvent(AddEditEvent.EndTimeChanged(it)) },
                    label = { Text("Hora de término") },
                    placeholder = { Text("HH:MM PM") }
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddEditContentPreview() {
    MyTasksTheme {
        AddEditContent(
            title = "",
            description = null,
            startTime = null,
            endTime = null,
            snackbarHostState = SnackbarHostState(),
            onEvent = {}
        )
    }
}
