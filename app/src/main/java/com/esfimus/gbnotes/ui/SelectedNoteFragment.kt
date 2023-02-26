package com.esfimus.gbnotes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.esfimus.gbnotes.R
import com.esfimus.gbnotes.data.Note
import com.esfimus.gbnotes.databinding.FragmentNoteSelectedBinding

private const val NOTE = "note"

class SelectedNoteFragment : Fragment() {

    private var note: Note? = null
    private var _ui: FragmentNoteSelectedBinding? = null
    private val ui get() = _ui!!

    companion object {
        fun newInstance(note: Note?) = SelectedNoteFragment().apply {
                arguments = Bundle().apply { putParcelable(NOTE, note) }
            }
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            note = it.getParcelable(NOTE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _ui = FragmentNoteSelectedBinding.inflate(inflater, container, false)
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        if (arguments != null) {
            ui.titleView.text = note?.getTitle()
            ui.textView.text = note?.getText()
            ui.dateView.text = note?.getDate()

            // FAB response: edit selected note in new fragment
            ui.editFab.setOnClickListener {
                openFragment(EditNoteFragment.newInstance(note))
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

    override fun onDestroyView() {
        _ui = null
        super.onDestroyView()
    }
}