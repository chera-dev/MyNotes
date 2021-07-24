package com.example.mynotes.ui

sealed class Data

data class Note(val noteTitle:String, val noteDetails:String, var noteType: Int):Data(){
    companion object{
        const val NOTES = 1
        const val ARCHIVED = 2
    }
}

data class Label(val labelId:Int, val labelName:String):Data()


