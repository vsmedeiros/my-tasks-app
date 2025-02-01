package com.example.mytasks.ui.feature

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mytasks.ui.theme.MyTasksTheme

@Composable
fun AddEditScreen() {
    AddEditContent()
}

@Composable
fun AddEditContent() {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
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
                value = "",
                onValueChange = {},
                label = { Text("Título") }, // ✅ Mantém o texto visível mesmo ao digitar
                placeholder = { Text("Digite o título") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                label = { Text("Descrição (opcional)") }, // ✅ Melhorado
                placeholder = { Text("Digite a descrição") }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddEditContentPreview() {
    MyTasksTheme {
        AddEditContent()
    }
}
