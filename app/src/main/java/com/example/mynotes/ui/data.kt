package com.example.mynotes.ui

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

sealed class Data


data class Note(val noteTitle:String, val noteDetails:String, var noteType: Int, var noteId: Int, var pinned:Int = UNPINNED):Data(){

    private val currentDateTime: LocalDateTime = LocalDateTime.now()
    val timeCreated = currentDateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
    val dateCreated = currentDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))

    private val labelOfThisNote = mutableListOf<Int>()

    fun addLabelToThisNote(labelId: Int){
        labelOfThisNote.add(labelId)
    }

    fun getLabelsOfThisNote():List<Int>{
        return labelOfThisNote
    }

    companion object{
        const val NOTES = 1
        const val ARCHIVED = 2
        const val PINNED = 1
        const val UNPINNED = 0
    }
}

data class Label(val labelId:Int, var labelName:String):Data(){
    private val notesIdInThisLabel = mutableListOf<Int>()

    fun addNoteToThisLabel(noteId: Int){
        notesIdInThisLabel.add(noteId)
    }

    fun getNotesOfThisLabel():List<Int>{
        return notesIdInThisLabel
    }
}

