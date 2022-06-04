package com.simurgeducation.newlastsimurg.ui.siralama

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SiralamaViewModel :ViewModel(){

    private val _text = MutableLiveData<String>().apply {
        value = "siralama ekranı"
    }
    val text: LiveData<String> = _text

}