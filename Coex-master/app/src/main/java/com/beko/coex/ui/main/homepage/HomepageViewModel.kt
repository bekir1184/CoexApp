package com.beko.coex.ui.main.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beko.coex.models.Room
import com.beko.coex.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomepageViewModel @Inject constructor(
    private val homepageRepository : HomepageRepository
) : ViewModel(){
    private val _userInfo  = MutableLiveData<User>()
    val userInfo : LiveData<User>
        get() = _userInfo

    private val _roomInfo = MutableLiveData<Room>()
    val roomInfo : LiveData<Room>
        get() = _roomInfo

    private val _setRoomBool = MutableLiveData<Boolean>()
    val setRoomBoolean : LiveData<Boolean>
        get() = _setRoomBool

    private val _setUserBool = MutableLiveData<Boolean>()
    val setUserBoolean : LiveData<Boolean>
        get() = _setUserBool

    private val _expenseUserList = MutableLiveData<MutableList<User>>()
    val expenseUserList : LiveData<MutableList<User>>
        get() = _expenseUserList

    fun getUserInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            _userInfo.postValue(homepageRepository.getUser())
        }
    }
    fun getRoomInfo(roomName : String){
        CoroutineScope(Dispatchers.IO).launch {
            _roomInfo.postValue(homepageRepository.getRoom(roomName))
        }
    }
    fun getExpenseUserList(roomName: String){
        CoroutineScope(Dispatchers.IO).launch {
            _expenseUserList.postValue(homepageRepository.getExpenseUserList(roomName))
        }
    }
    fun setRoom(room: Room , user: User){
        CoroutineScope(Dispatchers.IO).launch {
            _setRoomBool.postValue(homepageRepository.setRoom(room,user))
        }
    }
    fun setUser(user: User){
        CoroutineScope(Dispatchers.IO).launch {
            _setUserBool.postValue(homepageRepository.setUserForUid(user))
        }
    }



}


