package com.seher.debtproject.model

import android.content.Context

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.seher.debtproject.R

/**
 * Provide views to RecyclerView with data from dataSet.
 *
 * Initialize the dataset of the Adapter.
 *
 * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
 */
class CustomAdapter(
    private val context: Context,
    private val dataSet: MutableList<Debt>,
    private val managerObj: DataProvider,
    private val ColorDecide: Boolean
) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    //abstract class ViewHolder(v: View) : RecyclerView.ViewHolder(v)

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val textView: TextView = v.findViewById(R.id.titleTextView)
        val moneyView: TextView = v.findViewById(R.id.MoneyTextView)
        val rublesView: TextView = v.findViewById(R.id.currencyMoneyTextView)
        val personView: TextView = v.findViewById(R.id.personTextView)
        val layout: View = v.findViewById(R.id.cardLayout)

        lateinit var data: Debt
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_view, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Log.d(TAG, "Element $position set.")
        if (ColorDecide) {
            if (Utility.checkToday(dataSet[position].date)) {
                viewHolder.layout.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorLightBlue
                    )
                )
            }
        } else {
            if (dataSet[position].toMe)
                viewHolder.layout.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorPrimary
                    )
                )

            if (Utility.checkPast(dataSet[position].date))
                viewHolder.layout.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorPink
                    )
                )

        }

        if (Utility.checkToday(dataSet[position].date))
            if (dataSet[position].toMe)
                viewHolder.textView.text = context.resources.getString(R.string.return_today)
            else
                viewHolder.textView.text =
                    context.resources.getString(R.string.return_today_notme)
        else
            if (dataSet[position].toMe)
                viewHolder.textView.text =
                    context.resources.getString(R.string.return_string) + " " + Utility.getDate(
                        dataSet[position].date
                    )
            else
                viewHolder.textView.text =
                    context.resources.getString(R.string.return_notme) + " " + Utility.getDate(
                        dataSet[position].date
                    )

        viewHolder.moneyView.text =
            dataSet[position].moneyAmount.toString() + " " + Utility.gerCurrencySign(dataSet[position].currency)
        if (dataSet[position].currency != "RUB")
            viewHolder.rublesView.text =
                dataSet[position].moneyAmountRubles.toString() + " " + Utility.gerCurrencySign("RUB")
        viewHolder.personView.text = dataSet[position].person


    }


    override fun getItemCount() = dataSet.size

    companion object {
        private const val TAG = "CustomAdapter"
    }
}
