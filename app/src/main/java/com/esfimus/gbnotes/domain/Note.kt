package com.esfimus.gbnotes.domain

import java.time.LocalDateTime

class Note(private var title: String, private var text: String) {

    private var dateAndTime: String

    init {
        dateAndTime = currentDateAndTime()
    }

    private fun currentDateAndTime() = LocalDateTime.now().toString()

    fun getTitle() = title

    fun getText() = text

    fun getDateAndTime() = dateAndTime

    fun setTitle(newTitle: String) {
        title = newTitle
    }

    fun setText(newText: String) {
        text = newText
    }
}