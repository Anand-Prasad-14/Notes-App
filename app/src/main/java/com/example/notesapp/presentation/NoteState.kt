package com.example.notesapp.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.notesapp.data.Note

data class NoteState(
    val notes: List<Note> = emptyList(),
    val title: MutableState<String> = mutableStateOf(value = ""),
    val description: MutableState<String> = mutableStateOf(value = "")
)