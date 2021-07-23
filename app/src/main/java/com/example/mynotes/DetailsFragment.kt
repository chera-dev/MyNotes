package com.example.mynotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mynotes.ui.Note
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailsFragment : Fragment() {

    private val sharedSharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflate = inflater.inflate(R.layout.fragment_details, container, false)
        val args = DetailsFragmentArgs.fromBundle(requireArguments())
        val titleEditText:EditText = inflate.findViewById(R.id.titleTextViewInDetails)
        val detailsEditText:EditText = inflate.findViewById(R.id.detailsTextViewInDetails)
        var notePosition:Int? = null
        if(args.notePosition != null) {
            titleEditText.setText(args.noteTitle)
            detailsEditText.setText(args.noteDetails)
            notePosition = args.notePosition!!.toInt()
        }
        inflate.findViewById<FloatingActionButton>(R.id.fab_update_notes).setOnClickListener {
            val editedNote = Note(titleEditText.text.toString(),detailsEditText.text.toString())
            if (notePosition != null)
                sharedSharedViewModel.updateNotes(editedNote,notePosition)
            else
                sharedSharedViewModel.addNewNotes(editedNote)
            findNavController().popBackStack()
        }
        return inflate
    }

}