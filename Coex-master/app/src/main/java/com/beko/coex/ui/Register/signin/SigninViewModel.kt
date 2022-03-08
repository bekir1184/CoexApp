package com.beko.coex.ui.Register.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SigninViewModel @Inject constructor(
    private val signinRepository: SigninRepository
) : ViewModel(){

    private val _isDone = MutableLiveData<Boolean>()
    val isDone: LiveData<Boolean>
        get() = _isDone

    private val _isSend = MutableLiveData<Boolean>()
    val isSend: LiveData<Boolean>
        get() = _isSend

    fun loginAccount(mail: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isDone.postValue(signinRepository.loginAccount(mail, password))
        }
    }
    fun sendPasswordResetMail(mail: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isSend.postValue(signinRepository.sendPasswordResetMail(mail))
        }
    }
}