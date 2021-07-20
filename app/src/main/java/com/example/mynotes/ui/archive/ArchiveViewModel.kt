package com.example.mynotes.ui.archive

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArchiveViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Archive Fragment"
    }
    val text: LiveData<String> = _text
}