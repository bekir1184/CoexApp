package com.beko.coex.ui.main.addexpense

import android.util.Log
import com.beko.coex.models.Expense
import com.beko.coex.models.Room
import com.beko.coex.models.User
import com.beko.coex.ui.register.createroom.CreateRoomRepository
import com.beko.coex.utils.FirebasePath
import com.beko.coex.utils.Functions
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class AddExpenseRepository @Inject constructor() {

    suspend fun addExpenseRoom(room : Room , expense : Expense) : Boolean{
        room.expenseList.add(expense)
        return try {
            FirebasePath.roomRef.document(room.name).set(room).await()
            addExpenseUser(expense.user)
        } catch (e: Exception) {
            Log.e("TAG",e.message.toString())
            false
        }
    }
    private suspend fun addExpenseUser(user : User) : Boolean{
        Functions.getCurrentUserUid()?.let { uid ->
            return try {
                FirebasePath.userRef.document(uid).set(user).await()
                true
            } catch (e: Exception) {
                Log.e("TAG",e.message.toString())
                false
            }
        }
        return false
    }
}