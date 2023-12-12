package com.example.noteapp.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteapp.components.NoteButton
import com.example.noteapp.components.NoteInputText
import com.example.noteapp.data.NoteDataSource
import com.example.noteapp.models.Note
import com.example.noteapp.util.formatDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun NoteScreen(
    listNote: List<Note>,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit
) {
    val title = remember {
        mutableStateOf("")
    }
    val description = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val keyboadController =LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "NoteApp", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.LightGray),
                actions = {
                    Icon(
                        imageVector = Icons.Rounded.Notifications,
                        contentDescription = "action icon"
                    )
                }
            )
        },

        ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            NoteInputText(
                text = title.value,
                label = "title",
                onTextChange = { text ->
                    if (text.all { char -> char.isDefined() }) {
                        title.value = text
                    }
                }
            )
            NoteInputText(
                text = description.value,
                label = "add a note",
                onTextChange = { text ->
                    if (text.all { char -> char.isDefined() }) {
                        description.value = text
                    }
                }
            )
            Spacer(modifier = Modifier.height(5.dp))
            NoteButton(
                text = "Save",
                onClick = {
                    if (title.value.isNotEmpty() && description.value.isNotEmpty()) {
                        // add to list
                        onAddNote(
                            Note(
                            title = title.value,
                            description = description.value,
                        )
                        )
                        title.value = ""
                        description.value = ""
                        keyboadController?.hide()
                        Toast.makeText(context, "Note Added", Toast.LENGTH_SHORT).show()
                    }
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(listNote){ note ->
                    NoteRow(
                        note = note,
                        onNoteClicked = {
                            onRemoveNote(note)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NoteRow(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClicked: () -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(topEnd = 33.dp, bottomStart = 33.dp))
            .fillMaxWidth(),
        color = Color(0xFFDFE6EB),
        shadowElevation = 6.dp,
    ) {
        Column(
            modifier
                .clickable {
                    onNoteClicked()
                }
                .padding(horizontal = 14.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = note.title, style = MaterialTheme.typography.titleMedium)
            Text(text = note.description, style = MaterialTheme.typography.bodyMedium)
            Text(text = formatDate(note.entryDate.time), style = MaterialTheme.typography.bodySmall)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNoteScreen() {
    NoteScreen(listNote = NoteDataSource().loadNotes(), onAddNote = {}, onRemoveNote = {})
}