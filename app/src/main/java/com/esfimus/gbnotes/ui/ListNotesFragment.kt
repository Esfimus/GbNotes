package com.esfimus.gbnotes.ui

import android.app.AlertDialog
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esfimus.gbnotes.R
import com.esfimus.gbnotes.data.NotesDatabase
import com.esfimus.gbnotes.domain.*
import com.esfimus.gbnotes.domain.clicks.OnListItemClick
import com.esfimus.gbnotes.domain.clicks.OnListItemLongClick
import com.esfimus.gbnotes.domain.clicks.OnOptionsClick
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ListNotesFragment : Fragment() {

    private val notesDatabase = NotesDatabase()
    private var preferences: SharedPreferences? = null
    private val prefKey = "preferences"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // retrieving saved notes
        preferences = activity?.getSharedPreferences("Notes to save", MODE_PRIVATE)
        val savedNotes = preferences?.getString(prefKey, null)
        if (savedNotes != null) {
            try {
                val type: Type = object : TypeToken<List<Note>>() {}.type
                notesDatabase.addNotes(GsonBuilder().create().fromJson(savedNotes, type))
            } catch (e: Exception) {
                Toast.makeText(context, "Some shit happened", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Suppress("DEPRECATION")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = layoutInflater.inflate(R.layout.fragment_notes_list, container, false)

        // adding new note from arguments packed in mainActivity and received from NewNoteFragment
        if (arguments != null) {
            val newlyAddedNote = if (Build.VERSION.SDK_INT >= 33) {
                arguments?.getParcelable("note", Note::class.java)!!
            } else {
                arguments?.getParcelable("note")!!
            }
            notesDatabase.addNote(newlyAddedNote)
            // saving notes after adding new ones
            saveNotes()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDynamicList(view)
    }

    // saving notes after editing
    override fun onResume() {
        super.onResume()
        saveNotes()
    }

    private fun initDynamicList(view: View) {
        // creating dynamic list of items
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler)
        val customAdapter = RecyclerAdapter(notesDatabase.getNotesList())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = customAdapter

        // FAB response: opening new fragment to create new note
        view.findViewById<View>(R.id.add_fab).setOnClickListener {
            openFragment(NewNoteFragment.newInstance())
        }

        // reaction on list items clicks: open note in another fragment
        customAdapter.setClickListener(object : OnListItemClick {
            override fun onCLick(position: Int) {
                openFragment(SelectedNoteFragment.newInstance(notesDatabase.getNote(position)))
            }
        })

        // reaction on list items long clicks: open popup menu
        customAdapter.setLongClickListener(object : OnListItemLongClick {
            override fun onLongClick(position: Int, itemView: View) {
                popupMenu(customAdapter, position, itemView)
            }
        })

        // reaction on note's options clicks: open popup menu
        customAdapter.setOptionsClickListener(object : OnOptionsClick {
            override fun onOptionsClick(position: Int, itemView: View) {
                popupMenu(customAdapter, position, itemView)
            }
        })
    }

    /**
     * Creates popup menu for each selected view in list
     */
    private fun popupMenu(customAdapter: RecyclerAdapter, position: Int, itemView: View) {
        val popupMenu = PopupMenu(context, itemView)
        popupMenu.inflate(R.menu.popup_menu)
        popupMenu.show()
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.edit_popup -> {
                    openFragment(EditNoteFragment.newInstance(notesDatabase.getNote(position)))
                    true
                }
                R.id.delete_popup -> {
                    // building alert dialog before deletion
                    AlertDialog.Builder(context)
                        .setTitle("Confirm delete")
                        .setMessage("Are you sure you want to delete this note?")
                        .setCancelable(true)
                        .setNegativeButton("No") {
                                dialog, _ -> dialog.cancel()
                        }
                        .setPositiveButton("Yes") { _, _ ->
                            notesDatabase.deleteNote(position)
                            customAdapter.notifyItemRemoved(position)
                            // saving notes after deletion
                            saveNotes()
                        }
                        .show()
                    true
                }
                else -> false
            }
        }
    }

    /**
     * Opens given fragment on demand
     */
    private fun openFragment(fragment: Fragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    /**
     * Saves list of notes
     */
    private fun saveNotes() {
        val notesToSave: List<Note> = notesDatabase.getNotesList()
        val jsonNotes = GsonBuilder().create().toJson(notesToSave)
        preferences?.edit()?.putString(prefKey, jsonNotes)?.apply()
    }
}