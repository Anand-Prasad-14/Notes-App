package com.example.notesapp.presentation

import com.example.notesapp.data.Note

sealed interface NotesEvent {

    object SortNote: NotesEvent
    data class DeleteNote(var note: Note): NotesEvent
    data class SaveNote(
        var title: String,
        var description: String
    ): NotesEvent
}