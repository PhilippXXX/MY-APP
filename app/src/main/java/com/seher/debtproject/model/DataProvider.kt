package com.seher.debtproject.model

import android.content.Context
import androidx.recyclerview.widget.RecyclerView

class DataProvider(context: Context) {
    val dataSet: MutableList<Debt> = arrayListOf()
    var type = -1

    val dbHandler = DBHandler(context)
    lateinit var adapter: CustomAdapter

    private fun getToday(data: List<Debt>): List<Debt> {
        return data.filter { Utility.checkToday(it.date) or Utility.checkPast(it.date) or (it.date == 0L) }
    }

    fun getMe(data: List<Debt>): List<Debt> {
        return data.filter { it.toMe }
    }

    fun getToMe(data: List<Debt>): List<Debt> {
        return data.filter { !it.toMe }
    }

    fun initDebts(type: Int) {
        var tType = type
        if (type == -1)
            tType = this.type
        when (tType) {
            0 -> dbHandler.getDebts(this, ::getToday)
            1 -> dbHandler.getDebts(this, ::getMe)
            2 -> dbHandler.getDebts(this, ::getToMe)
        }
    }


    fun removeItem(viewHolder: RecyclerView.ViewHolder): String {
        val name = dataSet[viewHolder.adapterPosition].person
        dbHandler.deleteDebt(dataSet[viewHolder.adapterPosition])
        dataSet.clear()
        initDebts(type)
        return name
    }
}
