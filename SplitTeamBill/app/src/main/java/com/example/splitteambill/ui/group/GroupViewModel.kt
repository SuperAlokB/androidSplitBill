package com.example.splitteambill.ui.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GroupViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "2.5 %"
    }
    private val _vat = MutableLiveData<String>().apply {
        value = "10 %"
    }
    val cgst: LiveData<String> = _text
    val sggt: LiveData<String> = _text
    val vatTax: LiveData<String> = _vat
}