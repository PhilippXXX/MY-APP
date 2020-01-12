package com.seher.debtproject.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Debt::class], version = 2, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun debtDao(): DebtDao
}
