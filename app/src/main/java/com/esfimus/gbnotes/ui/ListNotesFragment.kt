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
import com.esfimus.gbnotes.R
import com.esfimus.gbnotes.data.Note
import com.esfimus.gbnotes.data.NotesDatabase
import com.esfimus.gbnotes.databinding.FragmentNotesListBinding
import com.esfimus.gbnotes.domain.*
import com.esfimus.gbnotes.domain.clicks.OnListItemClick
import com.esfimus.gbnotes.domain.clicks.OnListItemLongClick
import com.esfimus.gbnotes.domain.clicks.OnOptionsClick
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

private const val PREF_KEY = "preferences"
private const val SAVED_NOTES = "saved notes"
private const val NOTE = "note"

class ListNotesFragment : Fragment() {

    private val notesDatabase = NotesDatabase()
    private var preferences: SharedPreferences? = null
    private var _ui: FragmentNotesListBinding? = null
    private val ui get() = _ui!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // retrieving saved notes
        preferences = activity?.getSharedPreferences(SAVED_NOTES, MODE_PRIVATE)
        val savedNotes = preferences?.getString(PREF_KEY, null)
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
                              savedInstanceState: Bundle?): View {
        _ui = FragmentNotesListBinding.inflate(inflater, container, false)

        // adding new note from arguments packed in mainActivity and received from NewNoteFragment
        if (arguments != null) {
            val newlyAddedNote = if (Build.VERSION.SDK_INT >= 33) {
                arguments?.getParcelable(NOTE, Note::class.java)!!
            } else {
                arguments?.getParcelable(NOTE)!!
            }
            notesDatabase.addNote(newlyAddedNote)
            // saving notes after adding new ones
            saveNotes()
            arguments = null
        }
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDynamicList()
    }

    private fun initDynamicList() {
        // creating dynamic list of items
        val customAdapter = RecyclerAdapter(notesDatabase.getNotesList())
        ui.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = customAdapter
        }

        // FAB response: opening new fragment to create new note
        ui.addFab.setOnClickListener {
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
        PopupMenu(context, itemView).apply {
            inflate(R.menu.popup_menu)
            show()
            setOnMenuItemClickListener {
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
        preferences?.edit()?.putString(PREF_KEY, jsonNotes)?.apply()
    }

    override fun onResume() {
        super.onResume()
        saveNotes()
    }

    override fun onDestroyView() {
        _ui = null
        super.onDestroyView()
    }
}