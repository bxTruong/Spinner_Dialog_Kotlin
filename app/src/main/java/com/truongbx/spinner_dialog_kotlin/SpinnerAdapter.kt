package com.truongbx.spinner_dialog_kotlin

import android.annotation.SuppressLint
import android.content.Context
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SpinnerAdapter
import com.truongbx.spinner_dialog_kotlin.databinding.ItemDropDownViewSpinnerBinding
import com.truongbx.spinner_dialog_kotlin.databinding.ItemViewSpinnerBinding

class SpinnerAdapter(
    private val countryList: ArrayList<Country>,
    private val context: Context
) : SpinnerAdapter {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var binding =
            ItemDropDownViewSpinnerBinding
                .inflate(LayoutInflater.from(context), parent, false)
        binding.country = countryList[position]
        return binding.root
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var binding =
            ItemViewSpinnerBinding
                .inflate(LayoutInflater.from(context), parent, false)
        binding.country = countryList[position]
        return binding.root
    }

    override fun getItem(position: Int): Any {
        return countryList[position]
    }

    override fun getCount(): Int {
        return countryList.size
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun isEmpty(): Boolean {
        return false
    }

    override fun registerDataSetObserver(observer: DataSetObserver?) {}

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
    }

}