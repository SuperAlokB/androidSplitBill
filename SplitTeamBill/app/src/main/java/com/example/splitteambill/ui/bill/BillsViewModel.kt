package com.example.splitteambill.ui.bill

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BillsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Add Bill detail information "
    }
    val text: LiveData<String> = _text
}