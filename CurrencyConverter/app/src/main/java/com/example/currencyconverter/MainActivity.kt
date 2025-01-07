package com.example.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverter.ui.screens.CurrencyConverterScreen
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            CurrencyConverterTheme {
                Locale.setDefault(Locale.US)

//                GreetingPreview()
                CurrencyConverterScreen()
            }


//            // Tự động fetch dữ liệu khi Activity mở
//            LaunchedEffect(Unit) {
//                viewModel.fetchExchangeRates()
//            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    Text(
        text = "Hello $name!",
        modifier = modifier,

    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CurrencyConverterTheme {
        Greeting("Android")
    }
}
