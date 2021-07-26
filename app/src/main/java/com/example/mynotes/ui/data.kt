package com.example.mynotes.ui

import android.os.Build
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

sealed class Data


data class Note(val noteTitle:String, val noteDetails:String, var noteType: Int, var noteId: Int, var pinned:Int = UNPINNED):Data(){

    private val currentDateTime: LocalDateTime = LocalDateTime.now()
    val timeCreated = currentDateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
    val dateCreated = currentDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))

    companion object{
        const val NOTES = 1
        const val ARCHIVED = 2
        const val PINNED = 1
        const val UNPINNED = 0
    }
}

data class Label(val labelId:Int, val labelName:String):Data()

