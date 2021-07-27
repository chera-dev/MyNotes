package com.example.mynotes.ui.label

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.CreateMenu
import com.example.mynotes.MenuBottomDialog
import com.example.mynotes.R
import com.example.mynotes.SharedViewModel
import com.example.mynotes.databinding.FragmentLabelBinding
import com.example.mynotes.ui.*
import com.example.mynotes.ui.notes.NotesFragment
import com.google.android.material.snackbar.Snackbar

class LabelFragment : Fragment() ,ItemListener, LabelClickListener{

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
        labelList = sharedSharedViewModel.getLabel()
        recyclerAdapter = NotesAdapter(labelList , this ,null)
        //recyclerAdapter = NotesAdapter(labelList , onItemClick = {label,position -> onLabelClick(label as Label,position) }, onItemLongClick = {label,position -> onLabelLongClick(label as Label,position) })
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)

        setHasOptionsMenu(true)

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
        //Toast.makeText(requireContext(),"in label fragment clicked label ${data.labelName}",Toast.LENGTH_SHORT).show()
        val fragmentManager = activity?.supportFragmentManager
        val notesDialog = NotesDialogFragment(data.labelId,this)
        notesDialog.show(fragmentManager!!,"notesDialog")
        /*val transaction = activity?.supportFragmentManager?.beginTransaction()
        val notesFragment = NotesFragment()
        val args = Bundle()
        args.putInt("labelId",data.labelId)
        notesFragment.arguments = args
        transaction?.replace(R.id.nav_host_fragment_content_main,notesFragment)
        transaction?.commit()*/
        //findNavController().navigate(LabelFragmentDirections.actionNavLabelToNavNotes(data.labelId.toString()))
    }

    override fun onLongClick(position: Int) {
        val data:Label = recyclerAdapter.notesList[position] as Label
        Toast.makeText(requireContext(),"in label fragment long clicked label ${data.labelName}", Toast.LENGTH_SHORT).show()
        MenuBottomDialog(requireContext())
            .addItem(MenuBottomDialog.Operation("rename") {
                Toast.makeText(requireContext(),"rename clicked",Toast.LENGTH_SHORT).show()
                sharedSharedViewModel.renameLabel(data.labelId,"chera test")
                //change label name by alert dialog
                //add label by alert dialog
                labelList = sharedSharedViewModel.getLabel()
                recyclerAdapter.changeData(labelList)
            })
            .addItem(MenuBottomDialog.Operation("delete") {
                Toast.makeText(requireContext(),"delete clicked",Toast.LENGTH_SHORT).show()
                sharedSharedViewModel.deleteLabel(data.labelId)
                labelList = sharedSharedViewModel.getLabel()
                recyclerAdapter.changeData(labelList)
            }).show()
    }

    override fun onNoteClick(noteId: Int) {
        val data = sharedSharedViewModel.getNote(noteId)
        Snackbar.make(requireView(),"in label fragment ${data.noteTitle}",Snackbar.LENGTH_SHORT).show()
        findNavController().navigate(LabelFragmentDirections.actionNavLabelToDetailsFragment(data.noteTitle,data.noteDetails
        ,data.noteId.toString(),data.noteType.toString(),data.pinned.toString()))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val createMenu = CreateMenu(menu)
        createMenu.addMenuItem(Menu.NONE,1,1,"add new label",R.drawable.ic_outline_label_24,
            MenuItem.SHOW_AS_ACTION_ALWAYS or MenuItem.SHOW_AS_ACTION_WITH_TEXT, onclick = {itemTitle ->
                Toast.makeText(requireContext(), "$itemTitle clicked", Toast.LENGTH_SHORT).show()
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Add new label")
                val dialogLayout = layoutInflater.inflate(R.layout.add_label,null)
                val titleEditText = dialogLayout.findViewById<EditText>(R.id.label_title_edit_text)
                builder.setView(dialogLayout)
                builder.setPositiveButton("Add Label"){ _, _ ->
                    Toast.makeText(requireContext(),"test ${titleEditText.text} added",Toast.LENGTH_SHORT).show()
                    sharedSharedViewModel.addLabel(titleEditText.text.toString())
                    labelList = sharedSharedViewModel.getLabel()
                    recyclerAdapter.changeData(labelList)
                }
                builder.show()
            })
        super.onCreateOptionsMenu(menu, inflater)
    }
}