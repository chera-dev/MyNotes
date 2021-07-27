package com.example.mynotes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.SharedViewModel
import com.example.mynotes.ui.notes.NotesFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotesDialogFragment(val labelId:Int):DialogFragment(),ItemListener {
    private val sharedSharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: NotesAdapter
    private lateinit var notesList: List<Note>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notes_dialog,container)
        recyclerView = view.findViewById(R.id.notes_dialog_recycler_view)
        //recyclerView.layoutParams.height = 1700
        //recyclerView.layoutParams.width = 1500
        notesList = sharedSharedViewModel.getNotesOfTheLabel(labelId)
        if (notesList.isEmpty()) {
            Toast.makeText(requireContext(), "no notes found", Toast.LENGTH_SHORT).show()
            this.dismiss()
        }
        recyclerAdapter = NotesAdapter(notesList,this,null)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)

        return view
    }

    override fun onClick(position: Int) {
        val data:Note = recyclerAdapter.notesList[position] as Note
        Toast.makeText(requireContext(),"in dialog fragment clicked ${data.noteTitle}",Toast.LENGTH_SHORT).show()
        //Toast.makeText(requireContext(),"in notes fragment clicked note ${data.noteTitle}", Toast.LENGTH_SHORT).show()

        //not navigating
        /*view?.findNavController()?.navigate(NotesDialogFragmentDirections.actionNotesDialogFragmentToDetailsFragment(
            data.noteTitle,data.noteDetails,data.noteId.toString(),
            Note.NOTES.toString(), data.pinned.toString()))*/
        this.dismiss()
    }

    override fun onLongClick(position: Int) {
        TODO("Not yet implemented")
    }
}