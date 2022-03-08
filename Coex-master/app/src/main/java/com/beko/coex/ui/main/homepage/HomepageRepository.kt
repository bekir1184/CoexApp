package com.beko.coex.ui.main.homepage

import android.util.Log
import com.beko.coex.models.User
import com.beko.coex.utils.FirebasePath
import com.beko.coex.utils.Functions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomepageRepository @Inject constructor(){

    suspend fun getUser() : User =
        try {
            Functions.getCurrentUserUid()?.let { uid ->
                FirebasePath.userRef.document(uid).get().await().toObject(User::class.java)
            } ?: User()
        }catch (e : Exception){
            Log.e("TAG","HATA")
            User()
        }
}