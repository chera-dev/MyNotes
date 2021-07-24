package com.example.mynotes

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mynotes.ui.Note
import com.example.mynotes.ui.Note.Companion.ARCHIVED
import com.example.mynotes.ui.Note.Companion.NOTES
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
        var noteType:Int? =null
        if(args.notePosition != null && args.noteType != null) {
            titleEditText.setText(args.noteTitle)
            detailsEditText.setText(args.noteDetails)
            notePosition = args.notePosition!!.toInt()
            noteType = args.noteType!!.toInt()
        }
        inflate.findViewById<FloatingActionButton>(R.id.fab_update_notes).setOnClickListener {
            val editedNote = Note(titleEditText.text.toString(),detailsEditText.text.toString(), NOTES)
            if (notePosition != null) {
                if (noteType == ARCHIVED) {
                    editedNote.noteType = ARCHIVED
                    sharedSharedViewModel.updateArchivedNotes(editedNote, notePosition)
                }
                else
                    sharedSharedViewModel.updateNotes(editedNote, notePosition)
            }
            else
                sharedSharedViewModel.addNewNotes(editedNote)
            findNavController().popBackStack()
        }
        setHasOptionsMenu(true)
        return inflate
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val createMenu = CreateMenu(menu)
        createMenu.addMenuItem(Menu.NONE,1,2,"pin",R.drawable.ic_outline_push_pin_24
            ,MenuItem.SHOW_AS_ACTION_ALWAYS,onclick = { itemTitle -> Toast.makeText(requireContext(),"$itemTitle clicked",Toast.LENGTH_SHORT).show() })
        createMenu.addMenuItem(Menu.NONE,2,1,"delete",R.drawable.ic_baseline_delete_24,MenuItem.SHOW_AS_ACTION_ALWAYS
            ,onclick = {itemTitle -> Toast.makeText(requireContext(),"$itemTitle clicked",Toast.LENGTH_SHORT).show() })

        inflater.inflate(R.menu.main,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}