package com.simurgeducation.newlastsimurg.ui.sorular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GalleryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is soru sor Fragment"
    }
    val text: LiveData<String> = _text
}