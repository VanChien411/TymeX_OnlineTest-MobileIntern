package com.example.currencyconverter.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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


@Preview()
@Composable
fun CurrencyConverterScreen() {
    var viewModel: CurrencyViewModel = CurrencyViewModel()
    val exchangeRates by viewModel.exchangeRates.observeAsState()

    // UI chính
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),


        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextHeader("Chuyển đổi tiền tệ")

        Spacer(modifier = Modifier.height(10.dp))
        CutLeftBox()


    //        // Khi chưa có dữ liệu
//        if (exchangeRates == null) {
//            Text("Loading exchange rates...")
//        } else {
//            // Hiển thị tỷ giá
//            val rates = exchangeRates!!.rates // Lấy tỷ giá
//            Text("Base currency: ${exchangeRates!!.base}")
//
//            LazyColumn {
//                items(rates.entries.toList()) { rate ->
//                    Text("${rate.key}: ${rate.value}")
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Nút tải lại dữ liệu
//        Button(onClick = { viewModel.fetchExchangeRates() }) {
//            Text("Refresh Rates")
//        }
    }
}
@Composable
fun TextHeader(
    content: String,
    color: Color = Color.White,
    style: TextStyle = MaterialTheme.typography.headlineMedium, // Kiểu chữ mặc định
    fontWeight: FontWeight? = FontWeight.Bold, // Trọng lượng chữ tùy chỉnh
    textAlign: TextAlign = TextAlign.Center, // Căn chỉnh văn bản
    modifier: Modifier = Modifier.padding(0.dp).fillMaxWidth().background(Color.Black) // Modifier mặc định

) {
    Text(
        text = content,
        style = style,
        color = color,
        fontWeight = fontWeight,
        modifier = modifier,
        textAlign = textAlign
    )
}
@Composable
fun NumberMoney(
    number: Double,
    color: Color = Color.Black,
    style: TextStyle = MaterialTheme.typography.bodyMedium
) {
    val formattedNumber = String.format("%.2f", number) // Định dạng số với 2 chữ số thập phân
//    Text(
//        text = "$prefix$formattedNumber$suffix",
//        color = color,
//        style = style,
//        fontSize = 25.sp,
//        fontWeight = FontWeight.Bold,
//        modifier = Modifier
//            .height(70.dp)
//            .wrapContentHeight(Alignment.CenterVertically) // Căn giữa nội dung theo chiều dọc
//    )
    var text by remember { mutableStateOf(formattedNumber) }

    TextField(
        value = text,
        onValueChange = { newValue ->
            // Kiểm tra nếu giá trị nhập vào là một số
            if (newValue.all { it.isDigit() }) {
                text = newValue
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number // Chỉ hiển thị bàn phím số
        ),
        textStyle = TextStyle(color = color, fontWeight = FontWeight.Bold,fontSize = 25.sp,), // Màu văn bản của TextField
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
            .height(70.dp)
            .wrapContentHeight(Alignment.CenterVertically) // Căn giữa nội dung theo chiều dọc
    )
}

@Composable
fun TextLabel(content: String,fontSize: TextUnit = 20.sp, textAlign: TextAlign = TextAlign.Left,color: Color= Color.Black) {
    Text(
        text = content,
        style = MaterialTheme.typography.headlineMedium, // Kiểu chữ tiêu đề
        fontWeight = FontWeight.Bold,
        color =color,
        fontSize = fontSize,
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = textAlign,
    )

}
@Composable
fun TextWithDropdown(   style: TextStyle = MaterialTheme.typography.bodyLarge) {
    var expanded by remember { mutableStateOf(false) }
    var moneyType by remember { mutableStateOf("USD") }
    val items = listOf("USD", "EUR", "GBP", "INR")
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(70.dp)
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

            modifier = Modifier.padding(15.dp,0.dp).weight(1f) // Take up the remaining space
        ) {
            NumberMoney(number = 1234.56)
        }
        Column(
            horizontalAlignment = Alignment.End,

            modifier = Modifier.width(95.dp)

        ) {
            Row( verticalAlignment = Alignment.CenterVertically,) {
                Text(
                    text = moneyType,
                    fontSize = 25.sp,
                    style = style,
                    fontWeight = FontWeight.Bold,
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
                    items.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = { moneyType = item
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
        contentPadding = PaddingValues(20.dp),
        modifier = Modifier.padding(5.dp) // Thêm padding xung quanh nếu cần
    ) {
        Text(text = text,
            fontSize = 22.sp,
            style = MaterialTheme.typography.labelLarge)

    }
}
@Composable
fun CutLeftBox() {
    Surface(
        shape = AbsoluteCutCornerShape(topRightPercent = 10),
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
                        TextWithDropdown()
                        Spacer(modifier = Modifier.height(20.dp))
                        Image(
                            painter = painterResource(id = R.drawable.swap_calls), // Thay R.drawable.image bằng tên tệp ảnh của bạn
                            contentDescription = "Image Description", // Mô tả cho ảnh
                            modifier = Modifier.fillMaxWidth().height(50.dp) // Thêm modifiers nếu cần
                        )
                        TextLabel("Chuyển thành")
                        TextWithDropdown()
                    }

                }
                TextLabel("100usd = 2000.000vnd", fontSize = 25.sp, textAlign = TextAlign.Center)
                Row (
                    modifier = Modifier .padding(vertical = 10.dp) // Khoảng cách phía trên và dưới
                ){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Black)

                    )
                }

                Row(){
                    ButtonDefault(onClick = {}, "Chuyển đổi")
                    TextLabel("Thời gian cập nhập giá\n12/32/3333 hh/mm/ss", fontSize = 15.sp, textAlign = TextAlign.Right, color = Color.Gray)

                }


            }


        }

    }
}

