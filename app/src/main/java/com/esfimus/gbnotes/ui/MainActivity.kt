package com.esfimus.gbnotes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esfimus.gbnotes.R
import com.esfimus.gbnotes.domain.Communicator
import com.esfimus.gbnotes.data.Note
import com.esfimus.gbnotes.databinding.ActivityMainBinding

private const val NOTE = "note"

class MainActivity : AppCompatActivity(), Communicator {

    private lateinit var listNotesFragment: ListNotesFragment
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
     * Receives note object via interface and packs it for fragment with notes list
     */
    override fun passData(note: Note) {
        val bundle = Bundle()
        bundle.putParcelable(NOTE, note)
        listNotesFragment.arguments = bundle
    }
}