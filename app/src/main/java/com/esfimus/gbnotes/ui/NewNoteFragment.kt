package com.esfimus.gbnotes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.esfimus.gbnotes.R
import com.esfimus.gbnotes.domain.Communicator

class NewNoteFragment : Fragment() {

    private lateinit var communicator: Communicator

    companion object {
        fun newInstance() = NewNoteFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_note_new, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        val titleView = view.findViewById<TextView>(R.id.title_new)
        val textView = view.findViewById<TextView>(R.id.text_new)

        // FAB response: creating new note
        view.findViewById<View>(R.id.save_fab).setOnClickListener {
            val title: String = titleView.text.toString()
            val text: String = textView.text.toString()
            if (!"""\s*""".toRegex().matches(title) ||
                !"""\s*""".toRegex().matches(text)) {
                // sending two string variables to mainActivity via interface
                communicator = requireActivity() as Communicator
                communicator.passData(title, text)
            }
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}