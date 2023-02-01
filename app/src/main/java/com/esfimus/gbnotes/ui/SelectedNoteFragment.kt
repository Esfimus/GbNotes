package com.esfimus.gbnotes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.esfimus.gbnotes.R
import com.esfimus.gbnotes.domain.Note

private const val NOTE = "note"

class SelectedNoteFragment : Fragment() {
    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            note = it.getParcelable(NOTE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_note_selected, container, false)
    }

    companion object {
        fun newInstance(note: Note?) =
            SelectedNoteFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(NOTE, note)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            val noteTitle = view.findViewById<TextView>(R.id.title_view)
            val noteText = view.findViewById<TextView>(R.id.text_view)
            val noteDate = view.findViewById<TextView>(R.id.date_view)
            noteTitle.text = note?.getTitle()
            noteText.text = note?.getText()
            noteDate.text = note?.getDate()

            // FAB response: edit selected note in new fragment
            view.findViewById<View>(R.id.edit_fab).setOnClickListener {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_fragment_container, EditNoteFragment.newInstance(note))
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
            }
        }
    }
}