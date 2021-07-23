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
import com.example.mynotes.SharedViewModel
import com.example.mynotes.databinding.FragmentLabelBinding
import com.example.mynotes.ui.Label
import com.example.mynotes.ui.NotesAdapter

class LabelFragment : Fragment() {

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
        recyclerAdapter = NotesAdapter(labelList , onItemClick = {note,position -> onLabelClick(note as Label,position) })
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)

        return root
    }

    fun onLabelClick(label: Label,position:Int){
        Toast.makeText(requireContext(),"clicked label ${label.labelName}",Toast.LENGTH_SHORT).show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}