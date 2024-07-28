package com.example.splitteambill.data


data class User(
    var name: String,
    var Id: Int,
    var food_price : Double,
    //var list: ArrayList<String> = arrayListOf<String>(),
    var liquor_price: Double,
    var total_bill: Double

)