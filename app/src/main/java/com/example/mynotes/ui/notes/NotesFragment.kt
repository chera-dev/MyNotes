package com.example.mynotes.ui.notes

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.databinding.FragmentNotesBinding
import com.example.mynotes.ui.Note
import com.example.mynotes.ui.NotesAdapter
import com.example.mynotes.SharedViewModel
import com.google.android.material.snackbar.Snackbar

class NotesFragment : Fragment() {

    //private lateinit var notesViewModel: NotesViewModel
    private val sharedSharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentNotesBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: NotesAdapter
    private lateinit var notesList: List<Note>
    override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
    ): View? {
      //notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

      _binding = FragmentNotesBinding.inflate(inflater, container, false)
      val root: View = binding.root

      /*val textView: TextView = binding.textHome
      notesViewModel.text.observe(viewLifecycleOwner, Observer {
        textView.text = it
      })*/

      binding.fab.setOnClickListener { view ->
        /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
          .setAction("Action", null).show()*/
        view?.findNavController()?.navigate(NotesFragmentDirections.actionNavNotesToDetailsFragment(null,null,null))
      }

      //setHasOptionsMenu(true)

      notesList = sharedSharedViewModel.noteList
      recyclerView = binding.notesRecyclerView
      recyclerAdapter = NotesAdapter(notesList, onItemClick = {note,position -> onNoteClick(note as Note,position) })
      recyclerView.adapter = recyclerAdapter
      recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)

      /*val searchView: android.widget.SearchView = binding.searchBar
      searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
        //when search view is clicked
        //Toast.makeText(context,"clicked",Toast.LENGTH_SHORT).show()
      }
    searchView.setOnSearchClickListener {
      //no response
      Toast.makeText(context,"searched",Toast.LENGTH_SHORT).show()
    }

    searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        Toast.makeText(context,"search submitted",Toast.LENGTH_SHORT).show()

        return false
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        val newNoteList = sharedSharedViewModel.noteList as MutableList
        for (i in 0 until newNoteList.size) {
          if (newNoteList[i].noteTitle.contains(newText!!, ignoreCase = true) || newNoteList[i].noteDetails.contains(newText, ignoreCase = true)) {
            newNoteList.remove(newNoteList[i])
            Toast.makeText(context, "note changed", Toast.LENGTH_SHORT).show()
          }
        }
        notesList = newNoteList
        recyclerAdapter.notifyDataSetChanged()

        return false
      }

    })*/

    return root
  }

  fun onNoteClick(note:Note, notePosition: Int){
    Snackbar.make(requireView(),"in mainactivity method",Snackbar.LENGTH_SHORT).show()
    view?.findNavController()?.navigate(NotesFragmentDirections.actionNavNotesToDetailsFragment(note.noteTitle,note.noteDetails,notePosition.toString()))
  }

  /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    super.onCreateOptionsMenu(menu, inflater)
    //like layouts we inflate menu
    inflater.inflate(R.menu.main,menu)
  }*/

  override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
