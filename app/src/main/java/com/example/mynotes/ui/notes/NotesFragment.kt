package com.example.mynotes.ui.notes

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.MenuBottomDialog
import com.example.mynotes.databinding.FragmentNotesBinding
import com.example.mynotes.SharedViewModel
import com.example.mynotes.ui.*
import com.example.mynotes.ui.Note.Companion.NOTES
import com.example.mynotes.ui.Note.Companion.UNPINNED

class NotesFragment : Fragment(),ItemListener {

    //private lateinit var notesViewModel: NotesViewModel
    private val sharedSharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentNotesBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: NotesAdapter
    private lateinit var notesList: List<Note>
    //private var labelId:Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      //notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

      _binding = FragmentNotesBinding.inflate(inflater, container, false)
      val root: View = binding.root

        //val args = NotesFragmentArgs.fromBundle(requireArguments())
        //labelId = args.labelId?.toInt()

      /*val textView: TextView = binding.textHome
      notesViewModel.text.observe(viewLifecycleOwner, Observer {
        textView.text = it
      })*/

      binding.fab.setOnClickListener { view ->
        /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
          .setAction("Action", null).show()*/
        view?.findNavController()?.navigate(NotesFragmentDirections.actionNavNotesToDetailsFragment(null,null,null,null, null))
      }

      //setHasOptionsMenu(true)

        //if (labelId != null)
            //notesList = sharedSharedViewModel.getNotesOfTheLabel(labelId!!)
        //else
            notesList = sharedSharedViewModel.getNotes()
      recyclerView = binding.notesRecyclerView
      recyclerAdapter = NotesAdapter(notesList, this,sharedSharedViewModel)
      //recyclerAdapter = NotesAdapter(notesList, onItemClick = {note,position -> onNoteClick(note as Note,position) }, onItemLongClick = {note,position -> onNoteClick(note as Note,position) })
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

   /* fun onNoteLongClick(note: Note,notePosition: Int){
        Toast.makeText(requireContext(),"in notes fragment long clicked note ${note.noteTitle}", Toast.LENGTH_SHORT).show()
    }

  fun onNoteClick(note:Note, notePosition: Int){
      Toast.makeText(requireContext(),"in notes fragment clicked note ${note.noteTitle}", Toast.LENGTH_SHORT).show()
    view?.findNavController()?.navigate(NotesFragmentDirections.actionNavNotesToDetailsFragment(note.noteTitle,note.noteDetails,notePosition.toString(),
        NOTES.toString()))
  }*/

  /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    super.onCreateOptionsMenu(menu, inflater)
    //like layouts we inflate menu
    inflater.inflate(R.menu.main,menu)
  }*/

  override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(position: Int) {
        val data:Note = recyclerAdapter.notesList[position] as Note
        //Toast.makeText(requireContext(),"in notes fragment clicked note ${data.noteTitle}", Toast.LENGTH_SHORT).show()
        view?.findNavController()?.navigate(NotesFragmentDirections.actionNavNotesToDetailsFragment(data.noteTitle,data.noteDetails,data.noteId.toString(),
            NOTES.toString(), data.pinned.toString()))
    }

    override fun onLongClick(position: Int) {
        val data:Note = recyclerAdapter.notesList[position] as Note
        Toast.makeText(requireContext(),"in notes fragment long clicked note ${data.noteTitle}", Toast.LENGTH_SHORT).show()
        MenuBottomDialog(requireContext())
            .addItem(MenuBottomDialog.Operation("archive") {
                Toast.makeText(requireContext(),"archive clicked",Toast.LENGTH_SHORT).show()
                sharedSharedViewModel.addToArchive(data.noteId)
                notesList = sharedSharedViewModel.getNotes()
                recyclerAdapter.changeData(notesList)
            })
            .addItem(MenuBottomDialog.Operation("delete") {
                Toast.makeText(requireContext(),"delete clicked",Toast.LENGTH_SHORT).show()
                sharedSharedViewModel.deleteNote(data.noteId)
                notesList = sharedSharedViewModel.getNotes()
                recyclerAdapter.changeData(notesList)
            })
            .addItem(MenuBottomDialog.Operation("labels"){
                Toast.makeText(requireContext(),"label clicked",Toast.LENGTH_SHORT).show()
            })
            .addItem(MenuBottomDialog.Operation(if (data.pinned == UNPINNED)"pin note" else "unPin note"){
                Toast.makeText(requireContext(),"pinned clicked",Toast.LENGTH_SHORT).show()
                if (data.pinned == UNPINNED)
                    sharedSharedViewModel.pinNotes(data.noteId)
                else
                    sharedSharedViewModel.unpinNote(data.noteId)
                notesList = sharedSharedViewModel.getNotes()
                recyclerAdapter.changeData(notesList)
            }).show()
    }
}
