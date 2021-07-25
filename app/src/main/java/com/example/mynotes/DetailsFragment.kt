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
import com.example.mynotes.ui.Note.Companion.PINNED
import com.example.mynotes.ui.Note.Companion.UNPINNED
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailsFragment : Fragment() {

    private val sharedSharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var editedNote:Note
    private var pinned:Int? = null
    private var noteType:Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflate = inflater.inflate(R.layout.fragment_details, container, false)
        val args = DetailsFragmentArgs.fromBundle(requireArguments())
        val titleEditText:EditText = inflate.findViewById(R.id.titleTextViewInDetails)
        val detailsEditText:EditText = inflate.findViewById(R.id.detailsTextViewInDetails)
        var noteId:Int? = null
        if(args.noteId != null && args.noteType != null) {
            titleEditText.setText(args.noteTitle)
            detailsEditText.setText(args.noteDetails)
            noteId = args.noteId!!.toInt()
            noteType = args.noteType!!.toInt()
            pinned = args.pinned!!.toInt()
        }
        inflate.findViewById<FloatingActionButton>(R.id.fab_update_notes).setOnClickListener {
            editedNote = Note(titleEditText.text.toString(),detailsEditText.text.toString(), NOTES,0)
            if (noteId != null && noteType != null) {
                editedNote.noteId = noteId
                editedNote.noteType = noteType as Int
                editedNote.pinned = pinned!!
                sharedSharedViewModel.updateNotes(editedNote)
            }
            else
                sharedSharedViewModel.addNewNotes(editedNote)
            findNavController().popBackStack()
        }
        if (noteType != null)
            setHasOptionsMenu(true)
        return inflate
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val createMenu = CreateMenu(menu)
        //works fine.. but pin drawable is not changed
        if (pinned!! == PINNED){
            createMenu.addMenuItem(Menu.NONE, 1, 2, "unPin",
                R.drawable.ic_baseline_push_unpin_24, MenuItem.SHOW_AS_ACTION_ALWAYS, onclick = { itemTitle ->
                    Toast.makeText(requireContext(), "$itemTitle clicked", Toast.LENGTH_SHORT).show()
                    pinned = UNPINNED
                })
        }
        else{
            createMenu.addMenuItem(Menu.NONE, 1, 2, "pin",
                R.drawable.ic_outline_push_pin_24, MenuItem.SHOW_AS_ACTION_ALWAYS, onclick = { itemTitle ->
                    Toast.makeText(requireContext(), "$itemTitle clicked", Toast.LENGTH_SHORT).show()
                    pinned = PINNED
                })
        }
        createMenu.addMenuItem(Menu.NONE,2,1,"delete",R.drawable.ic_baseline_delete_24,MenuItem.SHOW_AS_ACTION_ALWAYS
            ,onclick = {itemTitle -> Toast.makeText(requireContext(),"$itemTitle clicked",Toast.LENGTH_SHORT).show() })

        inflater.inflate(R.menu.main,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}