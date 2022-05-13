package com.beko.coex.ui.main.homepage

import android.util.Log
import com.beko.coex.models.Expense
import com.beko.coex.models.Room
import com.beko.coex.models.User
import com.beko.coex.ui.register.joinroom.JoinRoomRepository
import com.beko.coex.utils.FirebasePath
import com.beko.coex.utils.Functions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomepageRepository @Inject constructor(){

    suspend fun getUser(): User =
        try {
            Functions.getCurrentUserUid()?.let { uid ->
                FirebasePath.userRef.document(uid).get().await().toObject(User::class.java)
            } ?: User()
        }catch (e : Exception){
            Log.e("TAG","HATA")
            User()
        }
    suspend fun getExpenseUserList(roomName : String) : MutableList<User>{
        return try{
            FirebasePath.userRef.whereEqualTo("room",roomName)
                .get()
                .await()
                .toObjects(User::class.java)
        }catch (e : java.lang.Exception){
            Log.e("HomePageRepository","HATA---> "+e.message.toString())
            mutableListOf()
        }
    }
    suspend fun getRoom(roomName : String) : Room =
        try {
            FirebasePath.roomRef.document(roomName).get().await().toObject(Room::class.java) ?: Room()
        }catch (e : Exception){
            Log.e("TAG","HATA")
            Room()
        }
    suspend fun setRoom(room : Room , user : User) : Boolean{
        return try {
            FirebasePath.roomRef.document(room.name).set(room).await()
            setUser(user)
        } catch (e: java.lang.Exception) {
            Log.e("TAG",e.message.toString())
            false
        }
    }
    private suspend fun setUser(user : User) : Boolean{
        Functions.getCurrentUserUid()?.let { uid ->
            return try {
                FirebasePath.userRef.document(uid).set(user).await()
                true
            } catch (e: java.lang.Exception) {
                Log.e("TAG",e.message.toString())
                false
            }
        }
        return false
    }
}