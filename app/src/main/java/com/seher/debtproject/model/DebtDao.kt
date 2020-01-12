package com.seher.debtproject.model

import androidx.room.*


@Dao
interface DebtDao {

    @Query("Select * FROM Debt")
    fun getAllDebts(): List<Debt>

    @Query("DELETE FROM Debt")
    fun deleteDebts()

    @Delete
    fun deleteDebt(debt: Debt)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createDebt(newDebt: Debt)
}
