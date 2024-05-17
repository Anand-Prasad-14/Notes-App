package com.example.notesapp.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.Note
import com.example.notesapp.data.NoteDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteViewModel(
    private var dao: NoteDao
) : ViewModel() {

    private var isSortedByDate = MutableStateFlow(value = true)
    private var notes = isSortedByDate.flatMapLatest {
        if (it) {
            dao.getOrderByDate()
        } else {
            dao.getOrderByTitle()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private var _state = MutableStateFlow(NoteState())
    val state = combine(_state, isSortedByDate, notes) { state, isSortedByDate, notes->
        state.copy(
            notes = notes
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    dao.deleteNote(event.note)
                }
            }

            is NotesEvent.SaveNote -> {
                val note = Note(
                    title = state.value.title.value,
                    description = state.value.description.value,
                    dateAdded = System.currentTimeMillis()
                )
                viewModelScope.launch {
                    dao.upsertNote(note = note)
                }
                _state.update {
                    it.copy(
                        title = mutableStateOf(value = ""), description = mutableStateOf(value = "")
                    )
                }
            }

            NotesEvent.SortNote -> {
                isSortedByDate.value = !isSortedByDate.value
            }
        }
    }
}