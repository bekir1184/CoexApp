package com.beko.coex.ui.Register.createroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beko.coex.models.Room
import com.beko.coex.models.User
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
    private val _isSetUserDone = MutableLiveData<Boolean>()
    val isSetUserDone: LiveData<Boolean>
        get() = _isSetUserDone

    fun createRoom(room : Room) {
        viewModelScope.launch(Dispatchers.IO) {
            _isDone.postValue(createRoomRepository.createRoom(room))
        }
    }
    fun setUser(user : User){
        viewModelScope.launch(Dispatchers.IO){
            _isSetUserDone.postValue(createRoomRepository.setUser(user))
        }
    }
}