package com.beko.coex.ui.register.createroom

import android.util.Log
import com.beko.coex.models.Room
import com.beko.coex.models.User
import com.beko.coex.utils.FirebasePath
import com.beko.coex.utils.Functions
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateRoomRepository @Inject constructor() {

    suspend fun createRoom(room : Room) :Boolean{
        Functions.getCurrentUserUid()?.let { uid ->
            return try {
                FirebasePath.roomRef.document(room.name).set(room).await()
                true
            } catch (e: Exception) {
                Log.e(TAG,e.message.toString())
                false
            }
        }
        return false
    }
    suspend fun setUser(user: User) : Boolean{
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

    companion object {
        private const val TAG = "Create Room Repository"
    }
}