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
        return inflater.inflate(R.layout.fragment_notes_list, container, false)
    }

    companion object {
        fun newInstance() = NotesListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDynamicList(view)
    }

    private fun initDynamicList(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler)
        val customAdapter = RecyclerAdapter(notesDatabase.getNotesList())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = customAdapter

        view.findViewById<View>(R.id.fab).setOnClickListener {
            notesDatabase.addNote(Note("title", "text"))
            recyclerView.scrollToPosition(0)
            customAdapter.notifyItemInserted(0)
        }
    }
}