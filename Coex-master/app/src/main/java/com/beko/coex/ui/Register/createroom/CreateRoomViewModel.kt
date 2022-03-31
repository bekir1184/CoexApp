package com.beko.coex.ui.Register.createroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beko.coex.models.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateRoomViewModel @Inject constructor(
    private val createRoomRepository : CreateRoomRepository
) : ViewModel(){

    private val _isDone = MutableLiveData<Boolean>()
    val isDone: LiveData<Boolean>
        get() = _isDone

    fun loginAccount(room : Room) {
        viewModelScope.launch(Dispatchers.IO) {
            _isDone.postValue(createRoomRepository.createRoom(room))
        }
    }
}