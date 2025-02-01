package com.example.mytasks.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mytasks.domain.Todo
import com.example.mytasks.domain.todo1
import com.example.mytasks.domain.todo2
import com.example.mytasks.ui.theme.MyTasksTheme

@Composable
fun TodoItem(
    todo: Todo,
    onCompletedChange: (Boolean) -> Unit = {},
    onItemClicked: () -> Unit = {},
    onDeleteClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onItemClicked,
        modifier = modifier.padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 2.dp,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = todo.isCompleted,
                onCheckedChange = onCompletedChange)

            Spacer(
                modifier = Modifier
                    .width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.titleLarge
                )

                todo.description?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = onDeleteClicked) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TodoItemPreview() {
    MyTasksTheme {
        TodoItem(
            todo = todo1,
            onCompletedChange = {},
            onItemClicked = {},
            onDeleteClicked = {})
    }
}

@Preview(showBackground = true,)
@Composable
private fun TodoItemPreviewCompleted() {
    MyTasksTheme {
        TodoItem(
            todo = todo2,
            onCompletedChange = {},
            onItemClicked = {},
            onDeleteClicked = {})
    }
}
