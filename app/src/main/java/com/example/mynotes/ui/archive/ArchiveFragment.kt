package com.example.mynotes.ui.archive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.MenuBottomDialog
import com.example.mynotes.SharedViewModel
import com.example.mynotes.databinding.FragmentArchiveBinding
import com.example.mynotes.ui.ItemListener
import com.example.mynotes.ui.Note
import com.example.mynotes.ui.Note.Companion.ARCHIVED
import com.example.mynotes.ui.NotesAdapter

class ArchiveFragment : Fragment(),ItemListener {

    private val sharedSharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentArchiveBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var archiveNotes: List<Note>
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: NotesAdapter

    override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentArchiveBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textSlideshow
        //textView.text = "This is Archive Fragment"

        archiveNotes = sharedSharedViewModel.archiveList
        recyclerView = binding.archiveRecyclerView
        recyclerAdapter = NotesAdapter(archiveNotes,  this)
        //recyclerAdapter = NotesAdapter(archiveNotes, onItemClick = { note, position -> onArchiveNotesClick(note as Note,position)},onItemLongClick = {note,position -> onArchiveNotesClick(note as Note,position)})
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)

        return root
    }

    /*fun onArchiveNotesClick(note:Note, notePosition: Int){
        Toast.makeText(requireContext(),"in archive fragment clicked note ${note.noteTitle}", Toast.LENGTH_SHORT).show()
        view?.findNavController()?.navigate(ArchiveFragmentDirections.actionNavArchiveToDetailsFragment(note.noteTitle,note.noteDetails,notePosition.toString(),
            ARCHIVED.toString()
        ))
    }

    fun onArchiveNoteLongClick(note: Note,notePosition: Int){
        Toast.makeText(requireContext(),"in archive fragment long clicked note ${note.noteTitle}", Toast.LENGTH_SHORT).show()
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(position: Int) {
        val data:Note = recyclerAdapter.notesList[position] as Note
        //Toast.makeText(requireContext(),"in archive fragment clicked note ${data.noteTitle}", Toast.LENGTH_SHORT).show()
        view?.findNavController()?.navigate(ArchiveFragmentDirections.actionNavArchiveToDetailsFragment(data.noteTitle,data.noteDetails,position.toString(),
            ARCHIVED.toString()))
    }

    override fun onLongClick(position: Int) {
        val data:Note = recyclerAdapter.notesList[position] as Note
        Toast.makeText(requireContext(),"in archive fragment long clicked note ${data.noteTitle}", Toast.LENGTH_SHORT).show()
        MenuBottomDialog(requireContext())
            .addItem(MenuBottomDialog.Operation("unarchive") {
                Toast.makeText(requireContext(),"unarchive clicked",Toast.LENGTH_SHORT).show()
                sharedSharedViewModel.removeFromArchive(position)
                recyclerAdapter.notifyDataSetChanged()
            })
            .addItem(MenuBottomDialog.Operation("delete") {
                Toast.makeText(requireContext(),"delete clicked",Toast.LENGTH_SHORT).show()
                sharedSharedViewModel.deleteNote(data)
                recyclerAdapter.changeData(sharedSharedViewModel.noteList.filter { it.noteType == Note.ARCHIVED })
            }).show()
    }
}