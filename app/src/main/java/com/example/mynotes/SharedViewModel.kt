package com.example.mynotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynotes.ui.Label
import com.example.mynotes.ui.Note
import com.example.mynotes.ui.Note.Companion.ARCHIVED
import com.example.mynotes.ui.Note.Companion.NOTES

class SharedViewModel : ViewModel() {

    private val _noteList = mutableListOf<Note>()
    val noteList: List<Note>
        get() = _noteList//.filter { it.noteType == NOTES }

    val archiveList: List<Note>
        get() = _noteList.filter { it.noteType == ARCHIVED }

    private val _labelList = mutableListOf<Label>()
    val labelList: List<Label>
        get() = _labelList

    private var nextNoteId:Int = 5
    private var nextLabelId:Int = 3

    init {
        _noteList.add(Note(1,"Chera","I'm a good girl", NOTES))
        _noteList.add(Note(2,"Dev","I'm a bad girl", NOTES))

        _labelList.add(Label(1,"important"))
        _labelList.add(Label(2,"not important"))

        _noteList.add(Note(3,"archived note one","details of archived note one", ARCHIVED))
        _noteList.add(Note(4,"archived note two","details of archived note two", ARCHIVED))
    }

    fun addNewNotes(newNote: Note){
        newNote.noteId = nextNoteId++
        _noteList.add(newNote)
    }

    fun updateNotes(updatedNote:Note,position:Int){
        _noteList[position] = updatedNote
    }

    fun addToArchive(position: Int){
        _noteList[position].noteType = ARCHIVED
    }

    fun updateArchivedNotes(updatedNote:Note,position:Int){
        //_noteList[position] = updatedNote
    }

    fun removeFromArchive(position: Int){
        _noteList[position].noteType = NOTES
    }

    fun deleteNote(deleteNote: Note){
        _noteList.remove(deleteNote)
    }

}