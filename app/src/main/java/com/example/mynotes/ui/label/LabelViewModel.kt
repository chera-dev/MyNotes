package com.example.mynotes.ui.label

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LabelViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Label Fragment"
    }
    val text: LiveData<String> = _text
}