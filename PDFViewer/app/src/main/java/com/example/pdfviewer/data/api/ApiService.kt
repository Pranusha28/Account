package com.example.pdfviewer.data.api

import com.example.pdfviewer.data.model.Account
import retrofit2.http.GET

interface ApiService {
    @GET("Fillaccounts/nadc/2024-2025")
    suspend fun getAccounts(): List<Account>
}
