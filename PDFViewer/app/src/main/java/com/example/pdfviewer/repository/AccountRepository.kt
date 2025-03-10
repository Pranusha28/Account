package com.example.pdfviewer.repository

import androidx.lifecycle.LiveData
import com.example.pdfviewer.data.api.ApiService
import com.example.pdfviewer.data.model.Account
import com.example.pdfviewer.database.AccountDao

class AccountRepository(private val accountDao: AccountDao, private val apiService: ApiService) {

    val allAccounts: LiveData<List<Account>> = accountDao.getAccounts()

    suspend fun fetchAccountsFromApi() {
        try {
            val response = apiService.getAccounts()
            response.forEach { accountDao.insertAccount(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun updateAccount(account: Account) {
        accountDao.updateAccount(account)
    }

    suspend fun deleteAccount(account: Account) {
        accountDao.deleteAccount(account)
    }
}

