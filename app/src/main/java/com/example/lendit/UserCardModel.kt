package com.example.lendit


import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.LocalDate


data class UserCardModel(
    val userName: String? = "",
    val contact: String? = "",
    val amount: Int? = 0,
    val interest: Int? = 0,
    val startDate: String? = "",
    val transactions: ArrayList<Transactions>? = arrayListOf<Transactions>()
){
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(): this("rohit", "9898493894", 10000,10, "${LocalDate.now().toString()}", arrayListOf<Transactions>())

}



data class Transactions(
    val amount: Int = 0,
    val date: String? = "",
    val type: String? = ""
): Serializable
