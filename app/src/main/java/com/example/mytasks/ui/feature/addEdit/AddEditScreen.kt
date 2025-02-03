package com.example.mytasks.ui.feature.addEdit

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
    navigateBack : () -> Unit,

) {
    val context = LocalContext.current.applicationContext
    val database = TodoDatabaseProvider.provide(context)
    val repository = TodoRepositoryImpl(
        dao = database.dao
    )
    val viewModel = viewModel<AddEditViewModel>{
       AddEditViewModel(repository = repository)

    }

    val title = viewModel.title
    val description = viewModel.description

    val snackbarHostState = remember {
        SnackbarHostState() }

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
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun AddEditContent(
    title: String = "",
    description: String? = null,
    snackbarHostState: SnackbarHostState,
    onEvent: (AddEditEvent) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(AddEditEvent.Save)
            }) {
                Icon(Icons.Default.Check, contentDescription = "Salvo!")
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }

    ) { paddingValues ->  // ✅ Corrigido para evitar `consumeWindowInsets(it)`
        Column(
            modifier = Modifier
                .fillMaxSize()  // ✅ Garante que a Column ocupe todo o espaço disponível
                .padding(paddingValues)  // ✅ Aplica o padding corretamente
                .padding(16.dp) // Padding extra para afastar do topo
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = {
                    onEvent(
                        AddEditEvent.TitleChanged(it))

                },
                label = { Text("Título") }, // ✅ Mantém o texto visível mesmo ao digitar
                placeholder = { Text("Digite o título") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = description ?: "",
                onValueChange = {
                    onEvent(
                        AddEditEvent.DescriptionChanged(it))

                },
                label = { Text("Descrição (opcional)") },
                placeholder = { Text("Digite a descrição") }
            )
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
            snackbarHostState = SnackbarHostState(),
            onEvent = {}
        )
    }
}
