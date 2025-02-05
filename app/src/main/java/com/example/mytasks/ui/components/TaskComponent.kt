package com.example.mytasks.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mytasks.domain.Todo
import com.example.mytasks.domain.todo1
import com.example.mytasks.domain.todo2
import com.example.mytasks.ui.theme.MyTasksTheme

@Composable
fun TaskComponent(
    todo: Todo, onCompletedChange: (Boolean) -> Unit = {},
    onItemClicked: () -> Unit = {},
    onDeleteClicked: () -> Unit = {},
) {
    val taskColor = listOf(
        Color(0xFFF1FBF0),
        Color(0xFFDBE6EA),
        Color(0xFFF1F4FF)
    ).random()
    Surface(onClick = onItemClicked) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${todo.startTime}",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,

            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .border(
                            border = BorderStroke(3.dp, Color.Black),
                            shape = CircleShape
                        )
                )

                HorizontalDivider(modifier = Modifier.width(6.dp), color = Color.Black)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(taskColor),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Checkbox(
                            checked = todo.isCompleted,
                            onCheckedChange = onCompletedChange
                        )

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = todo.title,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(
                                    start = 12.dp,
                                    top = 12.dp
                                )
                            )

                            if (todo.description != null) {
                                Text(
                                    text = todo.description,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(start = 12.dp),
                                    color = Color.Gray
                                )
                            }

                            Text(
                                text = "${todo.startTime} - ${todo.endTime}",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(
                                    start = 12.dp,
                                    bottom = 12.dp
                                )
                            )
                        }
                        IconButton(
                            onClick = onDeleteClicked
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete"
                            )
                        }
                    }

                    HorizontalDivider(
                        modifier = Modifier
                            .width(6.dp)
                            .weight(0.1f), color = Color.Black
                    )

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskComponentPreview() {
    MyTasksTheme {
        TaskComponent(
            todo = todo1,
            onCompletedChange = {},
            onItemClicked = {},
            onDeleteClicked = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskComponentPreviewCompleted() {
    MyTasksTheme {
        TaskComponent(
            todo = todo2,
            onCompletedChange = {},
            onItemClicked = {},
            onDeleteClicked = {})
    }
}