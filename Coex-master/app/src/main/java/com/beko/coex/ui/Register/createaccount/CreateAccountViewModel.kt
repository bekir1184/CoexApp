package com.beko.coex.ui.register.createaccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beko.coex.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val createAccountRepository: CreateAccountRepository
) : ViewModel() {
    private val _isDone = MutableLiveData<Boolean>()
    val isDone : LiveData<Boolean>
        get() = _isDone

    fun createUser(user : User, password :String){
        viewModelScope.launch(Dispatchers.IO) {
            _isDone.postValue(createAccountRepository.createUser(user,password))
        }
    }
}