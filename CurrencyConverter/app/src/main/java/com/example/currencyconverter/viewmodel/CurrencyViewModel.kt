package com.example.currencyconverter.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.network.ExchangeRatesDataResponse
import com.example.currencyconverter.data.repository.CurrencyRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class CurrencyViewModel() : ViewModel() {
    private val repository: CurrencyRepository = CurrencyRepository()
    private val _exchangeRates = MutableLiveData<ExchangeRatesDataResponse>()
    val exchangeRates: LiveData<ExchangeRatesDataResponse> = _exchangeRates
    private val _moneyTypes = MutableLiveData<Set<String>>()
    val moneyTypes: LiveData<Set<String>> get() = _moneyTypes
    private val _result = MutableLiveData<String>("")
    val result: LiveData<String> get() = _result

    private val _fromNumber = MutableLiveData<Double>(0.0)
    val fromNumber: LiveData<Double> get() = _fromNumber
    private val _toNumber = MutableLiveData<Double>(0.0)
    val toNumber: LiveData<Double> get() = _toNumber

    private val _fromRate = MutableLiveData<String>("")
    val fromRate: LiveData<String> get() = _fromRate
    private val _toRate = MutableLiveData<String>("")
    val toRate: LiveData<String> get() = _toRate
    private val _timeLine = MutableLiveData<String>("")
    val timeLine: LiveData<String> get() = _timeLine

    fun updateToNumber(newValue: Double) {
        _toNumber.value = newValue
    }
    fun updateFromNumber(newValue: Double) {
        _fromNumber.value = newValue
    }
    fun updateFromRate(newValue: String){
        _fromRate.value = newValue
    }
    fun updateToRate(newValue: String){
        _toRate.value = newValue
    }


    fun fetchExchangeRates() {
        viewModelScope.launch {
            val rates = repository.getExchangeRatesData()
            rates?.let {
                _exchangeRates.postValue(it)

                _moneyTypes.postValue(it.rates.keys)
                // Kiểm tra số lượng phần tử trong rates
                when (it.rates.size) {
                    1 -> {
                        // Nếu có 1 phần tử, gán cho cả _fromRate và _toRate
                        val firstKey = it.rates.keys.first()
                        _fromRate.postValue(firstKey)
                        _toRate.postValue(firstKey)
                    }
                    2 -> {
                        // Nếu có 2 phần tử, lấy 2 key đầu tiên và gán cho _fromRate và _toRate
                        val ratesList = it.rates.keys.take(2).toList()  // Lấy 2 phần tử đầu tiên của map rates
                        val a = ratesList[0]  // Key đầu tiên
                        val b = ratesList[1]  // Key thứ hai

                        _fromRate.postValue(a)  // Lấy key đầu tiên và gán cho _fromRate
                        _toRate.postValue(b)    // Lấy key thứ hai và gán cho _toRate
                    }
                    else -> {
                        // Nếu có nhiều hơn 2 phần tử, chỉ lấy 2 phần tử đầu tiên và gán cho _fromRate và _toRate
                        val ratesList = it.rates.keys.take(2).toList()  // Lấy 2 phần tử đầu tiên của map rates
                        val a = ratesList[0]  // Key đầu tiên
                        val b = ratesList[1]  // Key thứ hai

                        _fromRate.postValue(a)  // Lấy key đầu tiên và gán cho _fromRate
                        _toRate.postValue(b)    // Lấy key thứ hai và gán cho _toRate
                    }
                }
            } ?: run {
                Log.e("ExchangeRates", "Failed to fetch exchange rates")
            }

        }
    }

    @SuppressLint("DefaultLocale")
    fun exchangeRates(){
        viewModelScope.launch {

            val rates =_exchangeRates.value


// Lấy tỷ giá từ đồng tiền cơ sở (USD) sang `fromCurrency` và `toCurrency`
            val fromRate = rates?.rates?.get(_fromRate.value)
            val toRate = rates?.rates?.get(_toRate.value)

            // Kiểm tra nếu một trong hai tỷ giá không tồn tại
            if (fromRate == null || toRate == null) {
                Log.e("ExchangeRates", "Failed to fetch exchange rates")
            }
            else{

                _toNumber.value = _fromNumber.value?.div(fromRate)?.times(toRate)
                val newToNumber = String.format("%.3f", _toNumber.value ?: 0.0)
                _result.postValue(_fromNumber.value.toString()+ " " + _fromRate.value +" = "  + newToNumber +" "+_toRate.value)
                _timeLine.value = convertTimestampToDateTime(rates.timestamp)
            }


        }
    }

    fun rateSwap(){
        if (_toRate.value == null || _fromRate.value == null) {
            Log.e("ExchangeRates", "Failed to fetch exchange rates")
            return
        }
            val temp = _fromRate.value
            _fromRate.value = _toRate.value
            _toRate.value = temp.toString()
            exchangeRates()


    }
    fun convertTimestampToDateTime(timestamp: Long): String {
        val date = Date(timestamp * 1000) // Nhân với 1000 để chuyển giây thành mili giây
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) // Định dạng ngày giờ
        return format.format(date) // Chuyển thành chuỗi định dạng
    }

}