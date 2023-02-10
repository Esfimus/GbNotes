package com.esfimus.gbnotes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esfimus.gbnotes.R
import com.esfimus.gbnotes.domain.Communicator
import com.esfimus.gbnotes.domain.Note

class MainActivity : AppCompatActivity(), Communicator {

    private lateinit var listNotesFragment: ListNotesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listNotesFragment = ListNotesFragment()
        initNotesList()
    }

    private fun initNotesList() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, listNotesFragment)
            .commit()
    }

    /**
     * Receives two string variables via interface and packs this data for fragment with notes list
     */
    override fun passData(note: Note) {
        val bundle = Bundle()
        bundle.putParcelable("note", note)
        listNotesFragment.arguments = bundle
    }
}