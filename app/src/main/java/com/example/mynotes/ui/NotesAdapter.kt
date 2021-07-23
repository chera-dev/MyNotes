package com.example.mynotes.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.google.android.material.snackbar.Snackbar

class NotesAdapter(var notesList:List<Data>, var onItemClick: (Data,Int) -> Unit = { data: Data, i: Int -> })
    :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class NoteCardViewHolder(view: View): RecyclerView.ViewHolder(view){
        var item_title:TextView
        var item_details:TextView
        init {
            item_title = view.findViewById(R.id.item_title)
            item_details = view.findViewById(R.id.item_details)
            view.setOnLongClickListener {
                Snackbar.make(view,"long clicked ${item_title.text}",Snackbar.LENGTH_SHORT).show()
                return@setOnLongClickListener true
            }
            view.setOnClickListener {
                Toast.makeText(view.context, "clicked ${item_title.text}", Toast.LENGTH_SHORT).show()
                onItemClick(notesList[position], position)
            }
        }
    }

    inner class LabelCardViewHolder(view: View): RecyclerView.ViewHolder(view){
        var item_label:TextView
        init {
            item_label = view.findViewById(R.id.item_label)
            view.setOnClickListener {
                onItemClick(notesList[position],position)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data:Data = notesList[position]
        when(data) {
            is Note -> {
                holder as NoteCardViewHolder
                holder.item_title.text = data.noteTitle
                holder.item_details.text = data.noteDetails
            }
            is Label -> {
                holder as LabelCardViewHolder
                holder.item_label.text = data.labelName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_VIEW_TYPE_ONE)
            NoteCardViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.note_card_view,parent,false))
        else
            LabelCardViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.label_card_view,parent,false))
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (notesList[position] is Note) 1 else 2
    }

    companion object{
        const val ITEM_VIEW_TYPE_ONE = 1
        const val ITEM_VIEW_TYPE_TWO = 2
    }

}