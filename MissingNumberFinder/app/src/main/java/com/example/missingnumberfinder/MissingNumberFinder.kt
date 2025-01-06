package com.example.missingnumberfinder

import java.util.Scanner

class MissingNumberFinder {
}

fun main(){
    // Array Manipulation and Missing Number Problem

    val userInput = readUserInput()
    val resultConvert = processInput(userInput)
    val result = findMissingNumber(resultConvert)
    if(result != 0)
        println( "${result} (since ${result} is the missing number)")
    else
        println( "No missing numbers")


}

fun findMissingNumber(arr: IntArray): Int {
    if(arr.size == 0)
        return 0
    val I =  arr.min()

    val n = arr.size
    val totalSum = (n + I) * (n + I + 1) / 2 - (I - 1) * I / 2
    val actualSum = arr.sum()

    val isMissingNumber = n + I > (totalSum - actualSum)

    return if(isMissingNumber )
        totalSum - actualSum
    else
        0
}
fun readUserInput(): String {

    println("Enter an array of numbers (separated by spaces):")
    val scanner = Scanner(System.`in`)
    return scanner.nextLine()
}

fun processInput(input: String): IntArray {
    val inputArr = input.trim().split("\\s+".toRegex())

    // Check if array is empty
    if (inputArr.isEmpty()) {
        println("Error: Array cannot be empty!")
        return processInput(readUserInput())

    }

    for (item in inputArr) {
        if (!item.all { it.isDigit() }) {
            println("Error: '$item' is not a valid numeric digit!")
            return processInput(readUserInput())
        }
    }

    val numArray = inputArr.map { it.toInt() }

    return numArray.toIntArray()
}


