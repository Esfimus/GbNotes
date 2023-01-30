package com.esfimus.gbnotes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esfimus.gbnotes.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNotesList()
    }

    private fun initNotesList() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, NotesListFragment.newInstance())
            .commit()
    }
}