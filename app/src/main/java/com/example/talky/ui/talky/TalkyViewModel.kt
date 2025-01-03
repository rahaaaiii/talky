package com.example.talky.ui.talky


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TalkyViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Talky Fragment"
    }
    val text: LiveData<String> = _text
}