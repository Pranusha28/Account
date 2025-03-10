package com.example.pdfviewer.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pdfviewer.data.model.Account

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: Account)

    @Query("SELECT * FROM accounts")
    fun getAccounts(): LiveData<List<Account>>

    @Update
    suspend fun updateAccount(account: Account)

    @Delete
    suspend fun deleteAccount(account: Account)
}
