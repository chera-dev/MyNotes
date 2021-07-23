package com.example.mynotes.ui

sealed class Data

data class Note(val noteTitle:String, val noteDetails:String):Data()

data class Label(val labelName:String):Data()

