package com.beko.coex.ui.register.createaccount

import android.util.Log
import com.beko.coex.models.User
import com.beko.coex.utils.FirebasePath.userRef
import com.beko.coex.utils.Functions.getCurrentUserUid
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class CreateAccountRepository @Inject constructor() {
    suspend fun createUser(
        user : User,
        password : String
    ) : Boolean{
        return try {
            Firebase.auth.createUserWithEmailAndPassword(user.email,password).await()
            saveUserToFireStore(user)
        }catch (e : Exception){
            Log.e(TAG,e.message.toString())
            false
        }
    }
    private suspend fun saveUserToFireStore(user: User): Boolean {
        getCurrentUserUid()?.let { uid ->
            user.uid = uid.toString()
            return try {
                userRef.document(uid).set(user).await()
                true
            } catch (e: Exception) {
                Log.e(TAG,e.message.toString())
                false
            }
        }
        return false
    }

    companion object {
        private const val TAG = "Create Account Repository"
    }
}