package com.example.mytasks.ui.feature.addEdit

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mytasks.data.TodoDatabaseProvider
import com.example.mytasks.data.TodoRepositoryImpl
import com.example.mytasks.ui.theme.MyTasksTheme

@Composable
fun AddEditScreen() {
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

    AddEditContent(
        title = title,
        description = description,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun AddEditContent(
    title: String = "",
    description: String? = null,
    onEvent: (addEditEvent) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(addEditEvent.Save)
            }) {
                Icon(Icons.Default.Check, contentDescription = "Salvo!")
            }
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
                        addEditEvent.TitleChanged(it))

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
                        addEditEvent.DescriptionChanged(it))

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
            onEvent = {}
        )
    }
}
