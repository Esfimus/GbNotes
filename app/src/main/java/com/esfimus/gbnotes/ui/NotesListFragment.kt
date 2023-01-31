package com.esfimus.gbnotes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esfimus.gbnotes.R
import com.esfimus.gbnotes.data.NotesDatabase
import com.esfimus.gbnotes.domain.Note
import com.esfimus.gbnotes.domain.RecyclerAdapter

class NotesListFragment : Fragment() {

    private val notesDatabase = NotesDatabase()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = layoutInflater.inflate(R.layout.fragment_notes_list, container, false)

        // adding new note from another fragment
        if (arguments != null) {
            val titleNote = arguments?.getString("title")
            val textNote = arguments?.getString("text")
            notesDatabase.addNote(Note(titleNote, textNote))
        }

        initDynamicList(view)
        return view
    }

    private fun initDynamicList(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler)
        val customAdapter = RecyclerAdapter(notesDatabase.getNotesList())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = customAdapter

        // FAB response: opening new fragment to create new note
        view.findViewById<View>(R.id.add_fab).setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment_container, NewNoteFragment.newInstance())
                .addToBackStack("")
                .commit()
            recyclerView.scrollToPosition(0)
        }
    }
}