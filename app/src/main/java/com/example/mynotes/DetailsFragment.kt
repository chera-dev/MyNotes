package com.example.mynotes

import android.os.Build
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
import kotlin.math.PI

class DetailsFragment : Fragment() {

    private val sharedSharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var editedNote:Note
    private var pinned:Int? = null
    private var noteType:Int? = null
    private var noteId:Int? = null

    //use binding var

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflate = inflater.inflate(R.layout.fragment_details, container, false)
        val args = DetailsFragmentArgs.fromBundle(requireArguments())
        val titleEditText:EditText = inflate.findViewById(R.id.titleTextViewInDetails)
        val detailsEditText:EditText = inflate.findViewById(R.id.detailsTextViewInDetails)
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
                editedNote.noteId = noteId as Int
                editedNote.noteType = noteType as Int
                editedNote.pinned = pinned!!
                sharedSharedViewModel.updateNotes(editedNote)
            }
            else {
                editedNote.pinned = pinned!!
                sharedSharedViewModel.addNewNotes(editedNote)
            }
            findNavController().popBackStack()
        }
        setHasOptionsMenu(true)
        return inflate
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val createMenu = CreateMenu(menu)
        if (noteType != ARCHIVED){
            if (pinned == PINNED) {
                createMenu.addMenuItem(Menu.NONE, 1, 2, "unPin", R.drawable.ic_baseline_push_unpin_24,
                    MenuItem.SHOW_AS_ACTION_ALWAYS, onclick = { itemTitle ->
                        Toast.makeText(requireContext(), "$itemTitle clicked", Toast.LENGTH_SHORT).show()
                        createMenu.changeIcon(1, R.drawable.ic_outline_push_pin_24)
                        pinned = UNPINNED
                        if(noteId != null)
                            sharedSharedViewModel.unpinNote(noteId as Int)
                    })
            } else {
                createMenu.addMenuItem(Menu.NONE, 1, 2, "pin", R.drawable.ic_outline_push_pin_24,
                    MenuItem.SHOW_AS_ACTION_ALWAYS, onclick = { itemTitle ->
                        Toast.makeText(requireContext(), "$itemTitle clicked", Toast.LENGTH_SHORT).show()
                        createMenu.changeIcon(1, R.drawable.ic_baseline_push_unpin_24)
                        pinned = PINNED
                        if(noteId != null)
                            sharedSharedViewModel.pinNotes(noteId as Int)
                    })
            }
            createMenu.addMenuItem(Menu.NONE, 2, 3, "delete", R.drawable.ic_baseline_delete_24,
                MenuItem.SHOW_AS_ACTION_ALWAYS, onclick = { itemTitle ->
                    Toast.makeText(requireContext(), "$itemTitle clicked", Toast.LENGTH_SHORT).show()
                    sharedSharedViewModel.deleteNote(noteId as Int)
                    findNavController().popBackStack()
                })
            createMenu.addMenuItem(Menu.NONE, 3, 1, "archive", R.drawable.ic_baseline_archive_24,
                MenuItem.SHOW_AS_ACTION_ALWAYS, onclick = { itemTitle ->
                    Toast.makeText(requireContext(), "$itemTitle clicked", Toast.LENGTH_SHORT).show()
                    sharedSharedViewModel.addToArchive(noteId as Int)
                    findNavController().popBackStack()
                })
        }
        else{
            createMenu.addMenuItem(Menu.NONE, 1, 1, "unarchive", R.drawable.ic_baseline_unarchive_24,
                MenuItem.SHOW_AS_ACTION_ALWAYS, onclick = { itemTitle ->
                    Toast.makeText(requireContext(), "$itemTitle clicked", Toast.LENGTH_SHORT).show()
                    sharedSharedViewModel.removeFromArchive(noteId as Int)
                    findNavController().popBackStack()
                })
            createMenu.addMenuItem(Menu.NONE, 2, 2, "delete", R.drawable.ic_baseline_delete_24,
                MenuItem.SHOW_AS_ACTION_ALWAYS, onclick = { itemTitle ->
                    Toast.makeText(requireContext(), "$itemTitle clicked", Toast.LENGTH_SHORT).show()
                    sharedSharedViewModel.deleteNote(noteId as Int)
                    findNavController().popBackStack()
                })
        }
        inflater.inflate(R.menu.main,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}