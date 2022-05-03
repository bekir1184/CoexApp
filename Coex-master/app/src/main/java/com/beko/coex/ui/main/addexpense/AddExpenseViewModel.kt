package com.beko.coex.ui.main.addexpense

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beko.coex.models.Expense
import com.beko.coex.models.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val addExpenseRepository: AddExpenseRepository
) : ViewModel() {
    private val _isDone = MutableLiveData<Boolean>()
    val isDone : LiveData<Boolean>
        get()  = _isDone

    fun addExpense(room: Room, expense : Expense){
        CoroutineScope(Dispatchers.IO).launch {
            _isDone.postValue(addExpenseRepository.addExpenseRoom(room,expense))
        }
    }

}