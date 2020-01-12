package com.seher.debtproject.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.seher.debtproject.R
import com.seher.debtproject.model.DBHandler
import com.seher.debtproject.model.Debt
import com.seher.debtproject.model.Utility
import kotlinx.android.synthetic.main.activity_add_debt.*
import java.text.SimpleDateFormat
import java.util.*

class AddDebtActivity : AppCompatActivity() {
    private var dateValue: Long = 0
    private lateinit var dBHandler: DBHandler


    private fun dateSelect() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year1, monthOfYear, dayOfMonth ->

                val str = "" + dayOfMonth + "." + (monthOfYear + 1).toString() + "." + year1
                dateText.text = str
                val date = SimpleDateFormat("dd.MM.yyyy").parse(str)
                dateValue = date?.time ?: 0
            },
            year,
            month,
            day
        )

        dpd.show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_debt)
        dBHandler = DBHandler(this)
        editDateButton.setOnClickListener { dateSelect() }
        val aa = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            arrayListOf("RUB", "EUR", "USD")
        )
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner.adapter = aa

        saveButton.setOnClickListener {
            var amountRubles = amountInputEditText.text.toString().toDouble()
            val currency = spinner.selectedItem.toString()
            if (currency != "RUB")
                amountRubles = Utility.getInRubles(currency, amountRubles)
            val debt = Debt(
                0,
                nameInputEditText.text.toString(),
                amountInputEditText.text.toString().toDouble(),
                currency,
                dateValue,
                amountRubles,
                debtSwitch.isChecked
            )
            dBHandler.createDebt(debt) {}
            finish()
        }
    }
}
