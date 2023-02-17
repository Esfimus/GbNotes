package com.esfimus.gbnotes.domain

import com.esfimus.gbnotes.data.Note

interface Communicator {
    fun passData(note: Note)
}