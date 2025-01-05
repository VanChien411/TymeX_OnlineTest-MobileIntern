package com.example.currencyconverter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.network.ExchangeRatesDataResponse
import com.example.currencyconverter.data.repository.CurrencyRepository
import kotlinx.coroutines.launch

class CurrencyViewModel() : ViewModel() {
    private val repository: CurrencyRepository = CurrencyRepository()
    private val _exchangeRates = MutableLiveData<ExchangeRatesDataResponse>()
    val exchangeRates: LiveData<ExchangeRatesDataResponse> = _exchangeRates

    fun fetchExchangeRates() {
        viewModelScope.launch {
            val rates = repository.getExchangeRatesData()
            rates?.let { _exchangeRates.postValue(it) }
        }
    }
}