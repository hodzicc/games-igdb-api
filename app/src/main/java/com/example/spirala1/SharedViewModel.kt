package com.example.spirala1

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val isGameDetailsOpened = MutableLiveData<Boolean>(false)
    val gametitle = MutableLiveData<String>("")
    val empty = MutableLiveData<Boolean>(false)


}
