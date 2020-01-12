package com.seher.debtproject.model

import java.util.*

class Utility {
    companion object {
        val map: HashMap<String, String> = hashMapOf("RUB" to "\u20BD", "EUR" to "€", "USD" to "$")
        var usdRate = 63.toDouble()
        var eurRate = 70.toDouble()
        fun checkToday(date: Long): Boolean {
            val time = Calendar.getInstance()
            time.timeInMillis = date

            val now = Calendar.getInstance()

            return now.get(Calendar.DATE) == time.get(Calendar.DATE)
        }


        fun checkPast(date: Long): Boolean {
            return (date < System.currentTimeMillis()) and !checkToday(date)
        }


        fun getDate(date: Long): String {
            val format = java.text.SimpleDateFormat("dd.MM.yyyy")
            return format.format(Date(date)).toString()

        }


        fun gerCurrencySign(currency: String): String {
            return map[currency]!!
        }


        fun getInRubles(currency: String, amount: Double): Double {
            if (currency == "USD")
                return amount * usdRate

            return amount * eurRate
        }

    }
}