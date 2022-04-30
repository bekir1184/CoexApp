package com.beko.coex.ui.register.joinroom

import android.util.Log
import com.beko.coex.models.Room

import com.beko.coex.utils.FirebasePath
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject


class JoinRoomRepository @Inject constructor() {

    suspend fun joinRoom(roomName: String,roomPassword : String) : Boolean{
        return try {
            val roomFire = FirebasePath.roomRef.whereEqualTo("name",roomName)
                .get()
                .await()
                .toObjects(Room::class.java)
            return if(roomFire.isEmpty()){
                Log.d(TAG,"Oda adı bulunamadı")
                false
            }else{
                roomFire[0].password==roomPassword
            }

        } catch (e: Exception) {
            Log.e(TAG,e.message.toString())
            false
        }

        return false
    }
    suspend fun getRoom(roomName : String) : Room{
        return try {
            val room = FirebasePath.roomRef.whereEqualTo("name",roomName)
                .get()
                .await()
                .toObjects(Room::class.java)
            return room[0]

        } catch (e: Exception) {
            Log.e(TAG,e.message.toString())
            Room("","", mutableListOf(), emptyList())
        }
        Room("","", mutableListOf(), emptyList())
    }

    companion object {
        private const val TAG = "Join Room Repository"
    }
}