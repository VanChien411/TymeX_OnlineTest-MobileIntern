package com.example.currencyconverter.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    private val _moneyTypes = MutableLiveData<Set<String>>()
    val moneyTypes: LiveData<Set<String>> get() = _moneyTypes

    private val _fromRate = MutableLiveData<String>()
    val fromRate: LiveData<String> get() = _fromRate
    private val _toRate = MutableLiveData<String>()
    val toRate: LiveData<String> get() = _toRate

    var fromNumber by mutableStateOf(0.0)
        private set

    var toNumber by mutableStateOf(0.0)
        private set

    fun updateFromNumber(value: Double) {
        fromNumber = value
    }

    fun updateToNumber(value: Double) {
        toNumber = value
    }


    fun fetchExchangeRates() {
        viewModelScope.launch {
            val rates = repository.getExchangeRatesData()
            rates?.let {
                _exchangeRates.postValue(it)

                _moneyTypes.postValue(it.rates.keys)

            } ?: run {
                Log.e("ExchangeRates", "Failed to fetch exchange rates")
            }
        }
    }
    fun exchangeRates(fromNumber: Double = 0.0 , from:String, to: String  ){
        viewModelScope.launch {
            val rates = repository.getExchangeRatesData()
            rates?.let {
                _exchangeRates.postValue(it)

                _moneyTypes.postValue(it.rates.keys)

            } ?: run {
                Log.e("ExchangeRates", "Failed to fetch exchange rates")
            }

// Lấy tỷ giá từ đồng tiền cơ sở (USD) sang `fromCurrency` và `toCurrency`
            val fromRate = rates?.rates?.get(from)
            val toRate = rates?.rates?.get(to)
            // Kiểm tra nếu một trong hai tỷ giá không tồn tại
            if (fromRate == null || toRate == null) {
                Log.e("ExchangeRates", "Failed to fetch exchange rates")
            }
            else{
                updateFromNumber(fromNumber)
                updateToNumber(( fromNumber / fromRate ) * toRate)

            }


        }
    }

    fun rateSwap(){
        if (_toRate.value != null || _fromRate.value == null) {
            Log.e("ExchangeRates", "Failed to fetch exchange rates")
            return
        }

            exchangeRates(fromNumber = fromNumber, from = _toRate.value!!, to = _fromRate.value!!)


    }


}