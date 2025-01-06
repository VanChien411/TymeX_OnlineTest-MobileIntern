package com.example.currencyconverter.data.repository

import android.util.Log
import com.example.currencyconverter.data.network.CurrencyApi
import com.example.currencyconverter.data.network.ExchangeRatesDataResponse
import com.example.currencyconverter.data.network.RetrofitInstance
import retrofit2.Response
import java.io.IOException

class CurrencyRepository {
    private val apiKey:String = "69f3a103a09b46159b073d9032149b0e"
    private val api: CurrencyApi = RetrofitInstance.api
    val exchangeRatesData = ExchangeRatesDataResponse(
        disclaimer = "Usage subject to terms: https://openexchangerates.org/terms",
        license = "https://openexchangerates.org/license",
        timestamp = 1735977611,
        base = "USD",
        rates = mapOf(
            "AED" to 3.67301,
            "AFN" to 70.376654,
            "ALL" to 95.089447,
            "AMD" to 398.952925,
            "ANG" to 1.799312,
            "AOA" to 912.0,
            "ARS" to 1032.5,
            "AUD" to 1.608493,
            "USD" to  1.0
        )
    )

    //
    suspend fun getExchangeRatesData(): Response<ExchangeRatesDataResponse>? {
        var response: Response<ExchangeRatesDataResponse>? = null
        return try {
            response = api.getExchangeRatesData(apiKey)
            if (response.isSuccessful) {
                Log.d("API_CALL", "Response Body: ${response.body()}")
            } else {
                Log.e(
                    "API_CALL",
                    "Error Code: ${response.code()}, Error Body: ${response.errorBody()?.string()}"
                )
            }
            response
        } catch (e: IOException) {
            Log.e("API_CALL", "Network error: ${e.message}")
            null
        } catch (e: Exception) {
            Log.e("API_CALL", "Unexpected error: ${e.message}")
            null  // Trả về null nếu có lỗi khác
        }
    }
}