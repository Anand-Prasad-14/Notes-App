package com.example.notesapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.notesapp.data.NoteDatabase
import com.example.notesapp.presentation.AddNoteScreen
import com.example.notesapp.presentation.NoteView
import com.example.notesapp.presentation.NoteViewModel
import com.example.notesapp.ui.theme.NotesAppTheme

class MainActivity : ComponentActivity() {
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java,
            "notes.db"
        ).build()
    }

    private val viewModel by viewModels<NoteViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NoteViewModel(database.dao) as T
                }
            }
        }
    )

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {

                    val state by viewModel.state.collectAsState()
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "NoteView") {
                        composable("NoteView") {
                            NoteView(
                                state = state,
                                onEvent = viewModel::onEvent,
                                navController = navController
                            )
                        }
                        composable("AddNoteScreen") {
                            AddNoteScreen(
                                state = state,
                                onEvent = viewModel::onEvent,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}



