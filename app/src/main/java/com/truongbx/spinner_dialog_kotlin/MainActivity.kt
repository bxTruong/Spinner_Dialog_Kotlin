package com.truongbx.spinner_dialog_kotlin

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var countryList = ArrayList<Country>()
        for (i in 0 until 3) {
            countryList.add(Country("Việt Nam (+84)", R.drawable.ic_launcher_background))
        }
        spCountry.adapter = SpinnerAdapter(countryList, this)


    }

    fun openDatePickerDialog(view: View) {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]

        val datePickerDialog = DatePickerDialog(
            this, OnDateSetListener { _, year, month, day ->
                var date: String? = null
                date = if (day < 10 && month >= 9) {
                    String.format(getString(R.string.date1), day, month + 1, year)
                } else if (day < 10 && month < 9) {
                    String.format(getString(R.string.date2), day, month + 1, year)
                } else if (day > 10 && month < 9) {
                    String.format(getString(R.string.date3), day, month + 1, year)
                } else {
                    String.format(getString(R.string.date), day, month + 1, year)
                }
                tvShow.text = date
            }, year, month, day
        )
        datePickerDialog.show()
    }

    @SuppressLint("SetTextI18n")
    fun openTimePickerDialog(view: View) {
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        val timePickerDialog =
            TimePickerDialog(this,
                OnTimeSetListener { _, hourOfDay, minute ->
                    tvShow.text="$hourOfDay:$minute"
                }, hour, minute, true
            )

        timePickerDialog.show()

    }


}