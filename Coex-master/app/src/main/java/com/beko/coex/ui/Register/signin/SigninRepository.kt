package com.beko.coex.ui.Register.signin

import android.util.Log
import com.beko.coex.models.User
import com.beko.coex.utils.FirebasePath
import com.beko.coex.utils.Functions
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class SigninRepository @Inject constructor() {
    suspend fun loginAccount(mail : String ,password : String) : Boolean {
        return try {
            Firebase.auth.signInWithEmailAndPassword(mail,password).await()
            true
        }catch (e : Exception){
            Log.e(TAG,e.message.toString())
            false
        }
    }
    suspend fun sendPasswordResetMail(mail: String): Boolean {
        return try {
            Firebase.auth.sendPasswordResetEmail(mail).await()
            true
        } catch (e: Exception) {
            Log.e(TAG,e.message.toString())
            false
        }
    }

    //TODO: Google sign in
    /*
    suspend fun signInWithCredential(credential: AuthCredential): Boolean {
        return try {
            val userInfo = Firebase.auth.signInWithCredential(credential).await().additionalUserInfo
            if (userInfo?.isNewUser == true)
                getCurrentUser()?.let { user ->
                    saveUserToFireStore(
                        User(
                            name = user.displayName ?: "",
                            email = user.email ?: "invalid mail"
                        )
                    )
                }
            true
        } catch (e: Exception) {
            Log.e(TAG,e.message.toString())
            false
        }
    }
     */

    // If google sign in use
    private suspend fun saveUserToFireStore(user: User): Boolean {
        Functions.getCurrentUserUid()?.let { uid ->
            return try {
                FirebasePath.userRef.document(uid).set(user).await()
                true
            } catch (e: Exception) {
                Log.e(TAG,e.message.toString())
                false
            }
        }
        return false
    }

    private fun getCurrentUser(): FirebaseUser? = Firebase.auth.currentUser
    companion object {
        private const val TAG = "Sigin Repository"
    }
}