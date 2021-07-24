package com.example.mynotes.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.google.android.material.snackbar.Snackbar

class NotesAdapter(var notesList:List<Data>, private val itemListener: ItemListener)
    :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class NoteCardViewHolder(view: View): RecyclerView.ViewHolder(view){
        var item_title:TextView
        var item_details:TextView
        init {
            item_title = view.findViewById(R.id.item_title)
            item_details = view.findViewById(R.id.item_details)
            view.setOnLongClickListener {
                //Snackbar.make(view,"long clicked note ${item_title.text}",Snackbar.LENGTH_SHORT).show()
                //onItemLongClick(notesList[position],position)
                itemListener.onLongClick(position)
                return@setOnLongClickListener true
            }
            view.setOnClickListener {
                //Toast.makeText(view.context, "clicked note ${item_title.text}", Toast.LENGTH_SHORT).show()
                //onItemClick(notesList[position], position)
                itemListener.onClick(position)
            }
        }
    }

    fun changeData(newList: List<Data>){
        notesList = newList
        notifyDataSetChanged()
    }

    inner class LabelCardViewHolder(view: View): RecyclerView.ViewHolder(view){
        var item_label:TextView
        init {
            item_label = view.findViewById(R.id.item_label)
            view.setOnClickListener {
                //Toast.makeText(view.context, "clicked label ${item_label.text}", Toast.LENGTH_SHORT).show()
                //onItemClick(notesList[position],position)
                itemListener.onClick(position)
            }
            view.setOnLongClickListener {
                //Snackbar.make(view,"long clicked label ${item_label.text}",Snackbar.LENGTH_SHORT).show()
                //onItemLongClick(notesList[position],position)
                itemListener.onLongClick(position)
                return@setOnLongClickListener true
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data:Data = notesList[position]
        when(data) {
            is Note -> {
                holder as NoteCardViewHolder
                if (data.noteTitle.isNotEmpty()) {
                    holder.item_title.visibility = View.VISIBLE
                    holder.item_title.text = data.noteTitle
                }
                if (data.noteDetails.isNotEmpty()) {
                    holder.item_details.visibility = View.VISIBLE
                    holder.item_details.text = data.noteDetails
                }
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