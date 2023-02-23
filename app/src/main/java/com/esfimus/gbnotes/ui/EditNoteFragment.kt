package com.esfimus.gbnotes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.esfimus.gbnotes.data.Note
import com.esfimus.gbnotes.databinding.FragmentEditNoteBinding

private const val NOTE = "note"

class EditNoteFragment : Fragment() {

    private var note: Note? = null
    private var bindingNullable: FragmentEditNoteBinding? = null
    private val binding get() = bindingNullable!!

    companion object {
        fun newInstance(note: Note?) =
            EditNoteFragment().apply {
                arguments = Bundle().apply { putParcelable(NOTE, note) }
            }
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { note = it.getParcelable(NOTE) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        bindingNullable = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val noteTitle: TextView = binding.titleEdit
        val noteText: TextView = binding.textEdit
        val noteDate: TextView = binding.dateEdit
        if (arguments != null) {
            noteTitle.text = note?.getTitle()
            noteText.text = note?.getText()
            noteDate.text = note?.getDate()

            // FAB response: saving edited note
            binding.editSaveFab.setOnClickListener {
                note?.run {
                    setTitle(noteTitle.text.toString())
                    setText(noteText.text.toString())
                }
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }
}