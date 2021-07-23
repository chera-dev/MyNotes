package com.example.mynotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynotes.ui.Label
import com.example.mynotes.ui.Note

class SharedViewModel : ViewModel() {

    private val _noteList = mutableListOf<Note>()
    val noteList: List<Note>
        get() = _noteList

    private val _labelList = mutableListOf<Label>()
    val labelList: List<Label>
        get() = _labelList

    init {
        _noteList.add(Note("Chera","I'm a good girl"))
        _noteList.add(Note("Dev","I'm a bad girl"))

        _labelList.add(Label("important"))
        _labelList.add(Label("not important"))
    }

    fun addNewNotes(newNote: Note){
        _noteList.add(newNote)
    }

    fun updateNotes(updatedNote:Note,position:Int){
        _noteList[position] = updatedNote
    }
}