package com.example.notesapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AddNoteScreen(
    state: NoteState,
    navController: NavController,
    onEvent: (NotesEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(
                    NotesEvent.SaveNote(
                        title = state.title.value,
                        description = state.description.value
                    )
                )
                navController.popBackStack()
            }) {
                Icon(imageVector = Icons.Rounded.Check, contentDescription = null)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = state.title.value, onValueChange = {
                    state.title.value = it
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = {
                    Text(text = "Title")
                },
                textStyle = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            )

            OutlinedTextField(
                value = state.description.value, onValueChange = {
                    state.description.value = it
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = {
                    Text(text = "Description")
                },
                textStyle = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            )

        }
    }
}