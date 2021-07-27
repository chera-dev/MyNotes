package com.example.mynotes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.SharedViewModel
import com.example.mynotes.ui.notes.NotesFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotesDialogFragment(val labelId:Int, val labelClickListener: LabelClickListener):DialogFragment(),ItemListener {
    private val sharedSharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: NotesAdapter
    private lateinit var notesList: List<Note>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notes,container)
        view?.findViewById<SearchView>(R.id.search_bar)?.visibility = View.GONE
        view?.findViewById<FloatingActionButton>(R.id.fab)?.visibility = View.GONE
        recyclerView = view.findViewById(R.id.notes_recycler_view)
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
        labelClickListener.onNoteClick(data.noteId)
        this.dismiss()
    }

    override fun onLongClick(position: Int) {
        TODO("Not yet implemented")
    }
}