package com.beko.coex.ui.register.joinroom

import android.util.Log
import com.beko.coex.models.Room
import com.beko.coex.models.User

import com.beko.coex.utils.FirebasePath
import com.beko.coex.utils.Functions
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject


class JoinRoomRepository @Inject constructor() {


    suspend fun joinRoom(roomName : String, roomPassword : String,user : User) : Boolean{
        return try{
            val room = FirebasePath.roomRef.whereEqualTo("name",roomName)
                .get()
                .await()
                .toObjects(Room::class.java)
            if(room[0].password == roomPassword){
                return saveRoomtoFireStore(room[0]) && saveUsertoFireStore(roomName,user)
            }else{
                Log.e(TAG,"HATA---> Bir sıkıntı var")
            }
            false
        }catch (e : Exception ){
            Log.e(TAG,"HATA---> "+e.message.toString())
            false
        }
    }

    private suspend fun saveUsertoFireStore(roomName: String,user: User) :Boolean {

        Functions.getCurrentUserUid()?.let { uid ->
            user.uid = uid.toString()
            user.room = roomName
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

    private suspend fun saveRoomtoFireStore(room: Room): Boolean {
        Functions.getCurrentUserUid()?.let { uid ->
            room.userUidList.add(uid)
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

    companion object {
        private const val TAG = "Join Room Repository"
    }
}