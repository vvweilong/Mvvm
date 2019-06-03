package com.yezao.sanbiao

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

class FilterBottomSheetDialog(context: Context) : BottomSheetDialog(context) {

    var titleTextView: TextView? = null
    var filterItemContainer: LinearLayout? = null

    init {
        setContentView(R.layout.filter_layout)
        titleTextView = findViewById<TextView>(R.id.title_tv)
//        filterItemContainer = findViewById(R.id.linearlayout_container)
        setCanceledOnTouchOutside(false)
    }


    open fun setFilterItems(filterItems: ArrayList<FilterItem>):FilterBottomSheetDialog {
        filterItemContainer?.removeAllViews()

        for (filterItem in filterItems) {
            val filterItemView = FilterItemView(context)
            filterItemView.setTitle(filterItem.title)
            filterItemView.setItemData(filterItem.items)
            filterItemContainer?.addView(filterItemView)
        }



        return this
    }


    open class FilterItem(var title: String, var items: ArrayList<FilterItemView.ItemValueInterface>)
    open class FilterChose(var value:String,var name:String):FilterItemView.ItemValueInterface{
        override fun getItemText(): String {
            return name
        }

    }
}