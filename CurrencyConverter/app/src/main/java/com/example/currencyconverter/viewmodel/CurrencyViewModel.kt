package com.example.currencyconverter.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.network.ExchangeRatesDataResponse
import com.example.currencyconverter.data.repository.CurrencyRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import retrofit2.Response
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class CurrencyViewModel() : ViewModel() {
    private val repository: CurrencyRepository = CurrencyRepository()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> get() = _isLoading
    private val _networkError = MutableLiveData<Boolean?>()
    val networkError : LiveData<Boolean?> get() = _networkError
    private val _exchangeRates = MutableLiveData<Response<ExchangeRatesDataResponse>?>()
    val exchangeRates: LiveData<Response<ExchangeRatesDataResponse>?> = _exchangeRates

    private var _exchangeRatesChart = MutableLiveData<Response<ExchangeRatesDataResponse>?>()
    val exchangeRatesChart: LiveData<Response<ExchangeRatesDataResponse>?> = _exchangeRatesChart

    private val _moneyTypes = MutableLiveData<Set<String>>()
    val moneyTypes: LiveData<Set<String>> get() = _moneyTypes
    private val _result = MutableLiveData<String>("")
    val result: LiveData<String> get() = _result

    private val _fromNumber = MutableLiveData<Double?>(0.0)
    val fromNumber: LiveData<Double?> get() = _fromNumber
    private val _toNumber = MutableLiveData<Double?>(0.0)
    val toNumber: LiveData<Double?> get() = _toNumber

    private val _fromRate = MutableLiveData<String>("")
    val fromRate: LiveData<String> get() = _fromRate
    private val _toRate = MutableLiveData<String>("")
    val toRate: LiveData<String> get() = _toRate
    private val _timeLine = MutableLiveData<String>("")
    val timeLine: LiveData<String> get() = _timeLine

    private val _priceForm = MutableLiveData<Double?>(300.0)
    val priceForm: LiveData<Double?> get() = _priceForm
    private val _maxRange = MutableLiveData<Double?>(300.0)
    val maxRange: LiveData<Double?> get() = _maxRange
    fun updateToNumber(newValue: Double?) {
        _toNumber.value = newValue
    }
    fun updateFromNumber(newValue: Double?) {
        _fromNumber.value = newValue
    }
    fun updateFromRate(newValue: String){
        _fromRate.value = newValue
    }
    fun updateToRate(newValue: String){
        _toRate.value = newValue
    }
    fun updatePriceForm(newValue: Double?){
        _priceForm.value = newValue
    }
    fun updateMaxRange(newValue: Double?){
        _maxRange.value = newValue
    }
    fun updateExchangeRates(response: Response<ExchangeRatesDataResponse>?) {
        _exchangeRates.value = response
    }
    // MediatorLiveData để theo dõi sự thay đổi của _exchangeRates
    private val mediatorLiveData = MediatorLiveData<Response<ExchangeRatesDataResponse>?>().apply {
        // Khi _exchangeRates thay đổi, cập nhật _exchangeRatesChart
        addSource(_exchangeRates) { newExchangeRates ->
            _exchangeRatesChart.value = newExchangeRates
        }
    }

    init {
        // Gắn MediatorLiveData làm nguồn cập nhật cho _exchangeRatesChart
        _exchangeRatesChart = mediatorLiveData
    }



    fun fetchExchangeRates() {
        _isLoading.value = true
        _networkError.value = null
        viewModelScope.launch {
            try {
                val rates = repository.getExchangeRatesData()
                if(rates == null){
                    _networkError.value = true
                }
                _exchangeRates.postValue(rates)
                rates?.body()?.let {

                    _moneyTypes.postValue(it.rates.keys)
                    // Kiểm tra số lượng phần tử trong rates
                    if(_fromRate.value.isNullOrEmpty() || _toRate.value.isNullOrEmpty()){
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
                    }

                } ?: run {
                    Log.e("ExchangeRates", "Failed to fetch exchange rates")
                }
            } catch (e: Exception) {
                // Handle network error
                _networkError.value = true
            } finally {
                _isLoading.value = false
            }


        }
    }

    @SuppressLint("DefaultLocale")
    fun exchangeRates(){
        viewModelScope.launch {

            val rates =_exchangeRates.value?.body()

            val fromRate = rates?.rates?.get(_fromRate.value)
            val toRate = rates?.rates?.get(_toRate.value)

            // Kiểm tra nếu một trong hai tỷ giá không tồn tại
            if (fromRate == null || toRate == null) {
                Log.e("ExchangeRates", "Failed to fetch exchange rates")
            }
            else{

                _toNumber.value = _fromNumber.value?.div(fromRate)?.times(toRate)
                val decimalFormat: DecimalFormat  = DecimalFormat("#.##", DecimalFormatSymbols(Locale.US))

                _result.postValue((_fromNumber.value?.toString() ?: "0.0") + " " + _fromRate.value +" = "  + decimalFormat.format(_toNumber.value?:0.0) +" "+_toRate.value)
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
        val date = Date(timestamp * 1000)
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return format.format(date)
    }
    fun updateMaxRangeVM(){
        _maxRange.value = _priceForm.value?:10.0
    }

}