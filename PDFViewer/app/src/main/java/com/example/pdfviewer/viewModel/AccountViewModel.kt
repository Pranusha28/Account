package com.example.pdfviewer.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.pdfviewer.data.api.RetrofitInstance
import com.example.pdfviewer.data.model.Account
import com.example.pdfviewer.database.AccountDatabase
import com.example.pdfviewer.repository.AccountRepository
import kotlinx.coroutines.launch

class AccountViewModel(application: Application) : AndroidViewModel(application) {
    private val repository:AccountRepository
    val accounts:LiveData<List<Account>>
init{
    val accountDao=AccountDatabase.getDatabase(application).accountDao()
    repository=AccountRepository(accountDao,RetrofitInstance.api)
    accounts=repository.allAccounts
}


    fun fetchAccounts() {
        viewModelScope.launch {
            repository.fetchAccountsFromApi()
        }
    }

    fun updateAccount(account: Account) {
        viewModelScope.launch {
            repository.updateAccount(account)
        }
    }

    fun deleteAccount(account: Account) {
        viewModelScope.launch {
            repository.deleteAccount(account)
        }
    }
}
