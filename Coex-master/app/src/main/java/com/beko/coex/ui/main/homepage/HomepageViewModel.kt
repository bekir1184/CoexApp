package com.beko.coex.ui.main.homepage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beko.coex.models.User
import com.beko.coex.utils.FirebasePath
import com.beko.coex.utils.FirebasePath.userRef
import com.beko.coex.utils.Functions.getCurrentUserUid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class HomepageViewModel @Inject constructor(
    private val homepageRepository : HomepageRepository
) : ViewModel(){
    private val _userInfo  = MutableLiveData<User>()
    val userInfo : LiveData<User>
        get() = _userInfo

    fun getUserInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            _userInfo.postValue(homepageRepository.getUser())
        }
    }


}