package com.esfimus.gbnotes.domain

interface Communicator {
    fun passData(title: String, text: String)
}