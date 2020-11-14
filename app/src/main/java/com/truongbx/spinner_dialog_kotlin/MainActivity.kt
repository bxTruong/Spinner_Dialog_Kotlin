package com.truongbx.spinner_dialog_kotlin

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shawnlin.numberpicker.NumberPicker
import com.shawnlin.numberpicker.NumberPicker.OnScrollListener.SCROLL_STATE_IDLE
import com.truongbx.spinner_dialog_kotlin.databinding.ActivityMainBinding
import com.truongbx.spinner_dialog_kotlin.databinding.DialogCustomDatePickerDialogBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_custom_date_picker_dialog.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: DialogCustomDatePickerDialogBinding
    private lateinit var binding2: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var countryList = ArrayList<Country>()
        for (i in 0 until 3) {
            countryList.add(Country("Việt Nam (+84)", R.drawable.ic_launcher_background))
        }
        spCountry.adapter = SpinnerAdapter(countryList, this)

    }

    fun openDatePickerDialog(v: View) {
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
    fun openTimePickerDialog(v: View) {
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        val timePickerDialog =
            TimePickerDialog(
                this,
                OnTimeSetListener { _, hourOfDay, minute ->
                    tvShow.text = "$hourOfDay:$minute"
                }, hour, minute, true
            )
        timePickerDialog.show()
    }

    fun openCustomTimePickerDialog(v: View?) {
        val dialog = Dialog(this)
        binding = DataBindingUtil.inflate(
            dialog.layoutInflater,
            R.layout.dialog_custom_date_picker_dialog,
            null,
            false
        );
        dialog.setContentView(binding.root)
        val yearList = ArrayList<String>()
        val years: Array<String>
        val thisYear = Calendar.getInstance()[Calendar.YEAR]
        for (i in 1900..3000) {
            yearList.add(i.toString())
        }
        years = yearList.toTypedArray()
        binding.nbYear.minValue = 1900
        binding.nbYear.maxValue = 3000
        binding.nbYear.value = thisYear
        binding.nbYear.displayedValues = years

        val monthList = java.util.ArrayList<String>()
        val months: Array<String>
        var thisMonth = Calendar.getInstance()[Calendar.MONTH]
        thisMonth += 1
        for (i in 1..12) {
            monthList.add(i.toString())
        }
        months = monthList.toTypedArray()
        binding.nbMonth.minValue = 1
        binding.nbMonth.maxValue = 12
        binding.nbMonth.value = thisMonth
        binding.nbMonth.displayedValues = months
        val dayList = java.util.ArrayList<String>()
        val days: Array<String>
        val thisDay = Calendar.getInstance()[Calendar.DAY_OF_MONTH]
        for (i in 1..31) {
            dayList.add(i.toString())
        }
        days = dayList.toTypedArray()
        binding.nbDay.minValue = 1
        binding.nbDay.maxValue = 31
        binding.nbDay.value = thisDay
        binding.nbDay.displayedValues = days

        binding.nbDay.setOnScrollListener { picker: NumberPicker, scrollState: Int ->
            if (scrollState == SCROLL_STATE_IDLE) {
                if (returnDaysOfMonth(
                        binding.nbMonth.value,
                        binding.nbYear.value
                    ) < picker.value
                ) {
                    val about: Int =
                        days.size - returnDaysOfMonth(binding.nbMonth.value, binding.nbMonth.value)
                    picker.smoothScrollToPosition(days.size - about)
                }
            }
        }

        binding.nbMonth.setOnScrollListener { _: NumberPicker?, scrollState: Int ->
            if (scrollState == SCROLL_STATE_IDLE) {
                if (returnDaysOfMonth(
                        binding.nbMonth.value,
                        binding.nbYear.value
                    ) < binding.nbDay.value
                ) {
                    val about: Int =
                        days.size - returnDaysOfMonth(binding.nbMonth.value, binding.nbMonth.value)
                    binding.nbDay.smoothScrollToPosition(days.size - about)
                }
            }
        }

        binding.nbYear.setOnScrollListener { _: NumberPicker?, scrollState: Int ->
            if (scrollState == SCROLL_STATE_IDLE) {
                if (returnDaysOfMonth(
                        binding.nbMonth.value,
                        binding.nbYear.value
                    ) < binding.nbDay.value
                ) {
                    val about: Int =
                        days.size - returnDaysOfMonth(
                            binding.nbMonth.value,
                            binding.nbYear.value
                        )
                    binding.nbDay.smoothScrollToPosition(days.size - about)
                }
            }
        }

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun returnDaysOfMonth(month: Int, year: Int): Int {
        val daysInMonth: Int
        val leapYear: Boolean = checkLeap(year)
        daysInMonth =
            if (month == 4 || month == 6 || month == 9 || month == 11) 30 else if (month == 2) if (leapYear) 29 else 28 else 31
        return daysInMonth
    }

    private fun checkLeap(year: Int): Boolean {
        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = year
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365
    }

}