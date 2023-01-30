package com.esfimus.gbnotes.data

import com.esfimus.gbnotes.domain.Note

class NotesDatabase {

    private val notesList = mutableListOf<Note>()

    fun addNote(note: Note) {
        notesList.add(0, note)
    }

    fun getNotesList() = notesList
}