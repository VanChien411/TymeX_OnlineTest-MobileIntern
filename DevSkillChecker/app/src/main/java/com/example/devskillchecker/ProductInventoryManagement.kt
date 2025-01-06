package com.example.devskillchecker

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class ProductInventoryManagement {
}

data class Product(
    val name: String,
    val price: Double,
    val quantity: Int
)

fun main() {
    // Danh mục sản phẩm
    val productList = listOf(
        Product("Laptop", 999.99, 5),
        Product("Smartphone", 499.99, 10),
        Product("Tablet", 299.99, 0),
        Product("Smartwatch", 199.99, 3)
    )


    // Calculate the total inventory value
    val decimalFormat: DecimalFormat = DecimalFormat("#.##", DecimalFormatSymbols(Locale.US))
    println( decimalFormat.format(totalInventoryValue(productList)) )

    // Find the most expensive product
    val mostExpensiveProduct = mostExpensiveProduct(productList)
    if (mostExpensiveProduct != null) {
        println(mostExpensiveProduct)
    }
    else{
        println("Not found")
    }

    // Check if a product named "Headphones" is in stock
    val hasProduct = hasProduct(productList, "Headphones")
    println(hasProduct)

    // Sort products in descending/ascending order with options like by price, quantity
        // Sort by price ascending
        val sortedList1 = sortedBy(productList, isSortByPriceAsc = true)
        sortedList1.forEach { println(it) }

        // Sort by price descending
        val sortedList2 = sortedBy(productList, isSortByPriceAsc = false)
        sortedList2.forEach { println(it) }

        // Sort by quantity ascending
        val sortedList3 = sortedBy(productList, isSortByQuantityAsc = true)
         sortedList3.forEach { println(it) }

        //Sort by quantity descending
        val sortedList4 = sortedBy(productList, isSortByQuantityAsc = false)
        sortedList4.forEach { println(it) }

}

fun totalInventoryValue (productList: List<Product>) :Double{
    // Tính tổng giá trị hàng tồn kho
    return  productList.sumOf { it.price * it.quantity }

}

fun mostExpensiveProduct(productList: List<Product>): String?{
    // Tìm sản phẩm đắt nhất
    return productList.maxByOrNull { it.price }?.name

}
fun hasProduct(productList: List<Product>, productName: String) : Boolean{

    // Kiểm tra xem sản phẩm có tên "Tai nghe" có trong kho không
    return productList.any { it.name == productName }

}
fun sortedBy(productList: List<Product>, isSortByPriceAsc: Boolean?=null, isSortByQuantityAsc: Boolean? = null): List<Product> {

    val sortedByPrice = when (isSortByPriceAsc) {
        true -> productList.sortedBy { it.price }
        false -> productList.sortedByDescending { it.price }
        null -> productList
    }

    return when (isSortByQuantityAsc) {
        true -> sortedByPrice.sortedBy { it.quantity }
        false -> sortedByPrice.sortedByDescending { it.quantity }
        null -> sortedByPrice
    }
}


