package com.seher.debtproject.model

import android.content.Context
import android.widget.TextView

import com.seher.debtproject.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitAPI {
    companion object {

        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://data.fixer.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        private var service: ServerAPI

        init {
            service = retrofit.create(
                ServerAPI::class.java
            )
        }


        fun getMoney(context: Context, usd: TextView, eur: TextView) {

            val request = service.getMoney(
                getAPIToken(
                    context
                )
            )
            request.enqueue(object : Callback<MoneyResponse> {
                override fun onFailure(call: Call<MoneyResponse>, t: Throwable) {
                    println("Get categories failure")

                }

                override fun onResponse(
                    call: Call<MoneyResponse>,
                    response: Response<MoneyResponse>
                ) {
                    val facts = response.body()

                    val eurRate = facts!!.rates["RUB"]
                    val usdRate =
                        ((facts.rates["RUB"] ?: error("")) / (facts.rates["USD"] ?: error("")))
                    Utility.eurRate = eurRate!!
                    Utility.usdRate = usdRate
                    eur.text =
                        "1" + Utility.gerCurrencySign("EUR") + " = " + eurRate.toString() + Utility.gerCurrencySign(
                            "RUB"
                        )
                    usd.text =
                        "1" + Utility.gerCurrencySign("USD") + " = " + usdRate.toString() + Utility.gerCurrencySign(
                            "RUB"
                        )
                }
            })
        }

        private fun getAPIToken(context: Context): String {
            return context.getString(R.string.api_token)
        }


    }

}
