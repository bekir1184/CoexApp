package com.beko.coex.ui.register.JoinRoom

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
class JoinRoomViewModel @Inject constructor(
    private val joinRoomRepository: JoinRoomRepository
) : ViewModel(){

    private val _isDone = MutableLiveData<Boolean>()
    val isDone: LiveData<Boolean>
        get() = _isDone

    private val _room = MutableLiveData<Room>()
    val room : LiveData<Room>
        get() = _room

    fun loginRoom(roomName: String, roomPassword :String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isDone.postValue(joinRoomRepository.joinRoom(roomName,roomPassword))
        }
    }
    fun getRoom(roomName : String){
        viewModelScope.launch(Dispatchers.IO) {
            _room.postValue(joinRoomRepository.getRoom(roomName))
        }
    }
}