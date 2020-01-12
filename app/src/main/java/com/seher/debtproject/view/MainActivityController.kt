package com.seher.debtproject.view


import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seher.debtproject.R
import com.seher.debtproject.model.*
import kotlinx.android.synthetic.main.content_main2.*
import kotlinx.android.synthetic.main.nav_header_main2.*

class MainActivityController(private val activity: Main2Activity) {

    private lateinit var deleteIcon: Drawable
    private val swipeBg: ColorDrawable = ColorDrawable(Color.parseColor("#EF2727"))

    lateinit var managerObj: DataProvider
    var type = 0


    fun add() {
        val intent = Intent(activity, AddDebtActivity::class.java)
        activity.startActivityForResult(intent, 1)
    }


    fun getStats() {

        var me: Double = managerObj.getMe(managerObj.dataSet).sumByDouble { it.moneyAmountRubles }
        var toMe: Double =
            managerObj.getToMe(managerObj.dataSet).sumByDouble { it.moneyAmountRubles }
        activity.myDebtText.text = me.toString() + Utility.gerCurrencySign("RUB")
        activity.yourDebtText.text = toMe.toString() + Utility.gerCurrencySign("RUB")
        activity.budgetText.text = (toMe - me).toString() + Utility.gerCurrencySign("RUB")

    }


    fun setupSwipe() {
        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    managerObj.removeItem(viewHolder)
                    setup(type)
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    val itemView = viewHolder.itemView

                    swipeBg.setBounds(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )
                    swipeBg.draw(c)
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }

            }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(activity.debtsRecyclerView)
    }


    fun destroy() {
        val dbHandler = DBHandler(activity)
        dbHandler.closeDatabase()
    }


    fun setup(type: Int) {
        if (type != -1)
            this.type = type

        deleteIcon = ContextCompat.getDrawable(activity,
            R.drawable.ic_launcher
        )!!


        managerObj = DataProvider(activity)
        managerObj.initDebts(this.type)
        setupRecyclerView(this.type)
    }


    private fun setupRecyclerView(type: Int) {
        activity.debtsRecyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL, false
        )
        activity.debtsRecyclerView.setHasFixedSize(true)
        activity.debtsRecyclerView.adapter =
            CustomAdapter(activity, managerObj.dataSet, managerObj, (type == 0))
        managerObj.adapter = activity.debtsRecyclerView.adapter as CustomAdapter
    }
}
