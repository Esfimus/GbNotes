package com.esfimus.gbnotes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esfimus.gbnotes.R
import com.esfimus.gbnotes.domain.Communicator

class MainActivity : AppCompatActivity(), Communicator {

    private val listNotesFragment = ListNotesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNotesList()
    }

    private fun initNotesList() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, listNotesFragment)
            .commit()
    }

    override fun passData(title: String, text: String) {
        val bundle = Bundle()
        bundle.putString("title", title)
        bundle.putString("text", text)
        listNotesFragment.arguments = bundle
    }
}