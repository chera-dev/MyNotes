package com.example.mynotes

import androidx.lifecycle.ViewModel
import com.example.mynotes.ui.Label
import com.example.mynotes.ui.Note
import com.example.mynotes.ui.Note.Companion.ARCHIVED
import com.example.mynotes.ui.Note.Companion.NOTES
import com.example.mynotes.ui.Note.Companion.PINNED
import com.example.mynotes.ui.Note.Companion.UNPINNED

class SharedViewModel : ViewModel() {

    private val _noteList = mutableMapOf<Int,Note>()

    private val _labelList = mutableMapOf<Int,Label>()

    private var nextNoteId:Int = 5
    private var nextLabelId:Int = 3

    init {
        _noteList[1] = (Note("Chera","I'm a good girl", NOTES,1))
        _noteList[2] = (Note("Dev","I'm a bad girl", NOTES,2, 1))

        _labelList[1] = (Label(1, "important"))
        _labelList[2] = (Label(2,"not important"))

        _noteList[3] = (Note( "archived note one","details of archived note one", ARCHIVED,3))
        _noteList[4] = (Note( "archived note two","details of archived note two", ARCHIVED,4))
    }

    fun getNotes():List<Note>{
        val noteList = mutableListOf<Note>()
        for (i in _noteList.values)
            if (i.noteType == NOTES)
                noteList.add(i)
        //noteList.sortBy { it.pinned }
        noteList.sortByDescending { it.pinned }
        return noteList
    }

    fun getArchivedNotes():List<Note>{
        val archivedNoteList = mutableListOf<Note>()
        for (i in _noteList.values)
            if (i.noteType == ARCHIVED)
                archivedNoteList.add(i)
        return archivedNoteList
    }

    fun getLabel():List<Label>{
        val labelList = mutableListOf<Label>()
        for (i in _labelList.values)
            labelList.add(i)
        return labelList
    }
    //add label
    //rename label
    //delete label
    //new fragment to display notes of that label
    //onclick on those displayed notes goes to details fragment


    fun pinNotes(noteId: Int){
        _noteList[noteId]?.pinned = PINNED
    }

    fun unpinNote(noteId: Int){
        _noteList[noteId]?.pinned = UNPINNED
    }

    fun addNewNotes(newNote: Note){
        newNote.noteId = nextNoteId
        _noteList[nextNoteId++] = newNote
    }

    fun updateNotes(updatedNote:Note){
        _noteList[updatedNote.noteId] = updatedNote
    }

    fun addToArchive(noteId: Int){
        _noteList[noteId]?.noteType = ARCHIVED
    }

    fun removeFromArchive(noteId: Int){
        _noteList[noteId]?.noteType = NOTES
    }

    fun deleteNote(noteId: Int){
        _noteList.remove(noteId)
    }

}