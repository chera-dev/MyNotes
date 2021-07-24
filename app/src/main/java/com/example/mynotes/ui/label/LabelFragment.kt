package com.example.mynotes.ui.label

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.MenuBottomDialog
import com.example.mynotes.SharedViewModel
import com.example.mynotes.databinding.FragmentLabelBinding
import com.example.mynotes.ui.ItemListener
import com.example.mynotes.ui.Label
import com.example.mynotes.ui.Note
import com.example.mynotes.ui.NotesAdapter

class LabelFragment : Fragment() ,ItemListener{

    private var _binding: FragmentLabelBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: NotesAdapter
    private lateinit var labelList: List<Label>

    private val sharedSharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLabelBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textGallery
        //  textView.text = "This is label Fragment"

        recyclerView = binding.labelRecyclerView
        labelList = sharedSharedViewModel.labelList
        recyclerAdapter = NotesAdapter(labelList , this )
        //recyclerAdapter = NotesAdapter(labelList , onItemClick = {label,position -> onLabelClick(label as Label,position) }, onItemLongClick = {label,position -> onLabelLongClick(label as Label,position) })
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)

        return root
    }

    /*fun onLabelClick(label: Label,position:Int){
        Toast.makeText(requireContext(),"in label fragment clicked label ${label.labelName}",Toast.LENGTH_SHORT).show()
    }

    fun onLabelLongClick(label:Label, notePosition: Int){
        Toast.makeText(requireContext(),"in label fragment long clicked label ${label.labelName}", Toast.LENGTH_SHORT).show()
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(position: Int) {
        val data:Label = recyclerAdapter.notesList[position] as Label
        Toast.makeText(requireContext(),"in label fragment clicked label ${data.labelName}",Toast.LENGTH_SHORT).show()
    }

    override fun onLongClick(position: Int) {
        val data:Label = recyclerAdapter.notesList[position] as Label
        Toast.makeText(requireContext(),"in label fragment long clicked label ${data.labelName}", Toast.LENGTH_SHORT).show()
        MenuBottomDialog(requireContext())
            .addItem(MenuBottomDialog.Operation("rename") {
                Toast.makeText(requireContext(),"rename clicked",Toast.LENGTH_SHORT).show()
            })
            .addItem(MenuBottomDialog.Operation("delete") {
                Toast.makeText(requireContext(),"delete clicked",Toast.LENGTH_SHORT).show()
            }).show()
    }
}