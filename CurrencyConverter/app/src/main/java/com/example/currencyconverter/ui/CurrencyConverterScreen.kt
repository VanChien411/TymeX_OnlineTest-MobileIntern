package com.example.currencyconverter.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconverter.R
import com.example.currencyconverter.viewmodel.CurrencyViewModel

var viewModel: CurrencyViewModel = CurrencyViewModel()

@Preview()
@Composable
fun CurrencyConverterScreen() {
    LaunchedEffect(Unit) {
        viewModel.fetchExchangeRates()
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
            .background( MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        val isSmallScreen = maxWidth < 600.dp
        val i = maxWidth
        println(i)
        Column(
            modifier = Modifier
                .fillMaxSize()
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextHeader("Chuyển đổi tiền tệ")
            Loading()
            if(isSmallScreen){
                Column ( modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Column(
                        modifier = Modifier

                            .padding(16.dp)
                        ,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(10.dp))
                        // Kiểm tra các giá trị nullable và tránh lỗi null bằng cách sử dụng safe call (?.) và elvis operator (?:)
                        CutLeftBox()

                    }

                    //
                    Column(
                        modifier = Modifier

                            .padding(16.dp)
                        ,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(10.dp))
                        // Kiểm tra các giá trị nullable và tránh lỗi null bằng cách sử dụng safe call (?.) và elvis operator (?:)
                        CutLeftBox()

                    }
                }

            }else{
                Row ( modifier = Modifier
                    .fillMaxSize()
                    .background( MaterialTheme.colorScheme.background).windowInsetsPadding(WindowInsets.statusBars),
                    verticalAlignment = Alignment.CenterVertically)
                {

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp)
                        ,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(10.dp))
                        // Kiểm tra các giá trị nullable và tránh lỗi null bằng cách sử dụng safe call (?.) và elvis operator (?:)
                        CutLeftBox()

                    }

                    //
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp)
                        ,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(10.dp))
                        // Kiểm tra các giá trị nullable và tránh lỗi null bằng cách sử dụng safe call (?.) và elvis operator (?:)
                        CutLeftBox()

                    }
                }
            }
         }

    }


}
@Composable
fun Loading(){

    val exchangeRates by viewModel.exchangeRates.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState()
    val networkError by viewModel.networkError.observeAsState()
    val context = LocalContext.current
    if(isLoading == true){
        Text(
            text = "Đang tải dữ liệu...",
            color = MaterialTheme.colorScheme.onBackground
        )
    }
    if(exchangeRates?.errorBody() != null){
        Toast.makeText(context,"Lỗi api ${ exchangeRates?.message()}", Toast.LENGTH_LONG).show()

    }
    if(networkError == true){
        Toast.makeText(context, "Lỗi mạng hãy kiểm tra kết nối", Toast.LENGTH_LONG).show()
    }

}

@Composable
fun TextHeader(
    content: String,
    color: Color = MaterialTheme.colorScheme.onBackground,
    style: TextStyle = MaterialTheme.typography.headlineSmall, // Kiểu chữ mặc định
    fontWeight: FontWeight? = FontWeight.Bold, // Trọng lượng chữ tùy chỉnh
    textAlign: TextAlign = TextAlign.Center, // Căn chỉnh văn bản
    modifier: Modifier = Modifier.padding(0.dp,10.dp).fillMaxWidth() // Modifier mặc định

) {
    Text(
        text = content,
        style = style,
        color = color,
        fontWeight = fontWeight,
        modifier = modifier,
        textAlign = textAlign,


    )
}
@Composable
fun NumberMoney(
    number: Double,
    color: Color = Color.Black,
    onChageText: (Double) -> Unit = {}
) {

    OutlinedTextField(
        value = number.toString(),
        onValueChange = { newValue ->
            // Kiểm tra nếu giá trị nhập vào là một số hợp lệ
            if (newValue.isEmpty() || newValue.toDoubleOrNull() != null) {
                newValue.toDoubleOrNull()?.let { onChageText(it) }
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number // Chỉ hiển thị bàn phím số
        ),
        textStyle = TextStyle(color = color, fontWeight = FontWeight.Bold,fontSize = 20.sp,), // Màu văn bản của TextField
        colors = TextFieldDefaults.colors(
            //setting the text field background when it is focused
            focusedContainerColor = Color.White,

            //setting the text field background when it is unfocused or initial state
            unfocusedContainerColor = Color.White,

            //setting the text field background when it is disabled
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent, // Ẩn thanh gạch dưới khi có focus
            unfocusedIndicatorColor = Color.Transparent // Ẩn thanh gạch dưới khi không có focus
        ),

        modifier = Modifier
            .height(55.dp)
            .wrapContentHeight(Alignment.CenterVertically) // Căn giữa nội dung theo chiều dọc
    )
}

@Composable
fun TextLabel(content: String?,fontSize: TextUnit = 17.sp, textAlign: TextAlign = TextAlign.Left,color: Color= Color.Black) {
    content?.let {
        Text(
        text = it,
        style = MaterialTheme.typography.headlineMedium, // Kiểu chữ tiêu đề
        fontWeight = FontWeight.Bold,
        color =color,
        fontSize = fontSize,
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = textAlign,
    )
    }

}
@Composable
fun TextWithDropdown( money:Double,onChageText: (Double)-> Unit = {}, onChageRate:(String)-> Unit ={},  style: TextStyle = MaterialTheme.typography.bodyLarge, moneyType : String? = null, items : List<String>? = null) {
    var expanded by remember { mutableStateOf(false) }


    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(55.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)) // Giới hạn nền bên trong đường viền
            .background(Color.White) // Áp dụng nền trước
            .border(2.dp, Color.Black, RoundedCornerShape(8.dp)) // Viền bên ngoài
            .clickable {
                // Handle the click event here
                expanded = !expanded
            }

    ) {
        Column(

            modifier = Modifier.padding(1.dp,0.dp).weight(1f) // Take up the remaining space
        ) {
            NumberMoney(number = money, onChageText = onChageText)
        }
        Column(
            horizontalAlignment = Alignment.End,

            modifier = Modifier.width(95.dp)

        ) {
            Row( verticalAlignment = Alignment.CenterVertically,) {

                    Text(
                        text = moneyType?:"", // Hiển thị trạng thái local
                        fontSize = 20.sp,
                        style = style,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Right,
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentHeight(Alignment.CenterVertically)

                    )

                IconButton(onClick = {  expanded = !expanded}) {

                    Icon(Icons.Default.ArrowDropDown, contentDescription = "More options",Modifier.width(30.dp))
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    items?.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                onChageRate(item)
                   
                                expanded = !expanded}
                        )

                    }

                }

            }

        }


    }
}

@Composable
fun ButtonDefault(onClick: () -> Unit, text: String) {

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFD700), // Màu nền vàng
            contentColor = Color.Black         // Màu chữ đen
        ),
        shape = RoundedCornerShape(4.dp),
        contentPadding = PaddingValues(17.dp),
        modifier = Modifier.padding(5.dp) // Thêm padding xung quanh nếu cần
    ) {
        Text(text = text,
            fontSize = 22.sp,
            style = MaterialTheme.typography.labelLarge)


    }
}
@Composable
fun CutLeftBox() {

    val list by viewModel.moneyTypes.observeAsState()
    val result by viewModel.result.observeAsState()
    val fromNumber by viewModel.fromNumber.observeAsState()
    val toNumber by viewModel.toNumber.observeAsState()
    val fromRate by viewModel.fromRate.observeAsState(initial = "")
    val toRate by viewModel.toRate.observeAsState(initial = "")
    val timeLine by viewModel.timeLine.observeAsState(initial = "")

    Surface(
        shape = AbsoluteCutCornerShape(topRightPercent = 5),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier

        ) {
            Column {
                Box(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 16.dp, // Bo góc dưới bên trái
                                bottomEnd = 16.dp    // Bo góc dưới bên phải
                            )
                        )
                        .background(Color(0xFFFFD700))
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 4.dp,
                            bottom = 10.dp,
                        )
                ) {
                    Column {
                        TextLabel("Số tiền")
                        TextWithDropdown(fromNumber?:0.0, moneyType = fromRate , items = list?.toList() ?: emptyList(), onChageText ={ newValue -> viewModel.updateFromNumber(newValue)}, onChageRate = {newValue -> viewModel.updateFromRate(newValue)})
                        Spacer(modifier = Modifier.height(15.dp))
                        Image(
                            painter = painterResource(id = R.drawable.swap_calls), // Thay R.drawable.image bằng tên tệp ảnh của bạn
                            contentDescription = "Image Description", // Mô tả cho ảnh
                            modifier = Modifier.fillMaxWidth().height(30.dp)
                                .clickable {
                                    // Sự kiện khi nhấn vào ảnh
                                   viewModel.rateSwap()
                                }
                        )
                        TextLabel("Chuyển thành")
                        TextWithDropdown(toNumber?:0.0, moneyType = toRate, items = list?.toList() ?: emptyList(), onChageText ={ newValue -> viewModel.updateToNumber(newValue)}, onChageRate = {newValue -> viewModel.updateToRate(newValue)})
                    }

                }

                TextLabel(result, fontSize = 20.sp, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onBackground)
                Row (
                    modifier = Modifier .padding(vertical = 5.dp) // Khoảng cách phía trên và dưới
                ){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.onBackground)

                    )
                }

                Row(){
                    ButtonDefault(onClick = { viewModel.exchangeRates()}, "Chuyển đổi")
                    TextLabel("Thời gian cập nhập giá\n$timeLine", fontSize = 15.sp, textAlign = TextAlign.Right, color = Color.Gray)

                }


            }


        }

    }
}

