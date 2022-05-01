package com.beko.coex.ui.register.joinroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beko.coex.models.Room
import com.beko.coex.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinRoomViewModel @Inject constructor(
    private val joinRoomRepository: JoinRoomRepository
) : ViewModel(){
    private val _room = MutableLiveData<Boolean>()
    val room: LiveData<Boolean>
        get() = _room

    fun getRoom(roomName : String , roomPassword :String ,user: User){
        CoroutineScope(Dispatchers.IO).launch {
            _room.postValue(joinRoomRepository.joinRoom(roomName,roomPassword,user))
        }
    }


}