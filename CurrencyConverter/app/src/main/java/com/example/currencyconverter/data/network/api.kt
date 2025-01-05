package com.example.currencyconverter.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface CurrencyApi {
    @GET("latest.json")
    suspend fun getExchangeRatesData(
        @Query("app_id") appId: String
    ): Response<ExchangeRatesDataResponse> // Sử dụng Response để kiểm soát kết quả tốt hơn
}

data class ExchangeRatesDataResponse(
    val disclaimer: String,
    val license: String,
    val timestamp: Long, // Thay đổi thành Long
    val base: String,
    val rates: Map<String, Double>
)