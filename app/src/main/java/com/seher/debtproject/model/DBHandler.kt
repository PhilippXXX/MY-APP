package com.seher.debtproject.model

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DBHandler(context: Context) {

    fun createDebt(debt: Debt, onResult: () -> Unit) {

        GlobalScope.launch(Dispatchers.Main) {

            withContext(Dispatchers.IO) {

                db.debtDao().createDebt(debt)
            }
            onResult()
        }
    }

    fun deleteDebt(debt: Debt) {

        GlobalScope.launch(Dispatchers.Main) {

            withContext(Dispatchers.IO) {

                db.debtDao().deleteDebt(debt)
            }

        }
    }

    fun getDebts(managerObj: DataProvider, onResult: (List<Debt>) -> List<Debt>) {

        GlobalScope.launch(Dispatchers.Main) {

            val debts = withContext(Dispatchers.IO) {

                db.debtDao().getAllDebts()

            }

            managerObj.dataSet.clear()
            managerObj.dataSet.addAll(onResult(debts))
            managerObj.adapter.notifyDataSetChanged()
        }
    }


    private val db = Room.databaseBuilder(
        context,
        DataBase::class.java,
        DATABASE_FILE_NAME
    ).build()

    fun closeDatabase() {
        if (db.isOpen) {
            db.openHelper.close()
        }
    }

    companion object {
        const val DATABASE_FILE_NAME = "database"
    }
}
