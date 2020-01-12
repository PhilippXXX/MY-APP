package com.seher.debtproject.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
data class Debt(
    @PrimaryKey(autoGenerate = true) var id: Int, var person: String,
    var moneyAmount: Double,
    var currency: String,
    var date: Long,
    var moneyAmountRubles: Double,
    var toMe: Boolean
) : Serializable
