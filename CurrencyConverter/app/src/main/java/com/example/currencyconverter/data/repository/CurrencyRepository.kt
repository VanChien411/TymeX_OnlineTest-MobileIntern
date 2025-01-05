package com.example.currencyconverter.data.repository

import android.util.Log
import com.example.currencyconverter.data.network.CurrencyApi
import com.example.currencyconverter.data.network.ExchangeRatesDataResponse
import com.example.currencyconverter.data.network.RetrofitInstance
import java.io.IOException

class CurrencyRepository {
    private val apiKey:String = "69f3a103a09b46159b073d9032149b0e"
    private val api: CurrencyApi = RetrofitInstance.api

    suspend fun getExchangeRatesData(): ExchangeRatesDataResponse? {
        return try {
            val response = api.getExchangeRatesData(apiKey)
            if (response.isSuccessful) {
                Log.d("API_CALL", "Response Body: ${response.body()}")
                response.body()
            } else {
                Log.e(
                    "API_CALL",
                    "Error Code: ${response.code()}, Error Body: ${response.errorBody()?.string()}"
                )
                null
            }
        } catch (e: IOException) {
            Log.e("API_CALL", "Network error: ${e.message}")
            null
        } catch (e: Exception) {
            Log.e("API_CALL", "Unexpected error: ${e.message}")
            null
        }
    }
}