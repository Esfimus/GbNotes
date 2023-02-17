package com.esfimus.gbnotes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esfimus.gbnotes.domain.Communicator
import com.esfimus.gbnotes.data.Note
import com.esfimus.gbnotes.databinding.FragmentNoteNewBinding

class NewNoteFragment : Fragment() {

    private lateinit var communicator: Communicator
    private var bindingNullable: FragmentNoteNewBinding? = null
    private val binding get() = bindingNullable!!

    companion object {
        fun newInstance() = NewNoteFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        bindingNullable = FragmentNoteNewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val titleView = binding.titleNew
        val textView = binding.textNew

        // FAB response: creating new note
        binding.saveFab.setOnClickListener {
            val title: String = titleView.text.toString()
            val text: String = textView.text.toString()
            if (!"""\s*""".toRegex().matches(title) ||
                !"""\s*""".toRegex().matches(text)) {
                // sending two string variables to mainActivity via interface
                communicator = requireActivity() as Communicator
                val newNote = Note(title, text)
                communicator.passData(newNote)
            }
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bindingNullable = null
    }
}