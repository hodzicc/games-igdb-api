package ba.etf.rma23.projekat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val isGameDetailsOpened = MutableLiveData<Boolean>(false)
    val gametitle = MutableLiveData<String>("")
    val gameid = MutableLiveData<Int>(1)

}
