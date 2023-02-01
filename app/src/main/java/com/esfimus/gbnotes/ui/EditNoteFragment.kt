package com.esfimus.gbnotes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.esfimus.gbnotes.R
import com.esfimus.gbnotes.domain.Note

private const val NOTE = "note"

class EditNoteFragment : Fragment() {
    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            note = it.getParcelable(NOTE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_note, container, false)
    }

    companion object {
        fun newInstance(note: Note?) =
            EditNoteFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(NOTE, note)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            val noteTitle = view.findViewById<TextView>(R.id.title_edit)
            val noteText = view.findViewById<TextView>(R.id.text_edit)
            val noteDate = view.findViewById<TextView>(R.id.date_edit)
            noteTitle.text = note?.getTitle()
            noteText.text = note?.getText()
            noteDate.text = note?.getDate()

            // FAB response: saving edited note
            view.findViewById<View>(R.id.edit_save_fab).setOnClickListener {
                note?.setTitle(noteTitle.text.toString())
                note?.setText(noteText.text.toString())
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }
}