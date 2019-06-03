package com.yezao.sanbiao

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialogFragment
import android.support.transition.Transition
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.text.BoringLayout
import android.text.Layout
import android.transition.TransitionManager
import android.util.DisplayMetrics
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class FilterDialogFragment() : BaseBottomDialogFragment() {

    var titleTextView: TextView? = null
    var filterItemContainer: LinearLayout? = null


    private fun initChildView(dialog: AlertDialog) {
        titleTextView = dialog.findViewById<TextView>(R.id.title_tv)
//        filterItemContainer = dialog.findViewById(R.id.linearlayout_container)
//        filterItemContainer?.removeAllViews()
//        if (datalist != null) {
//            for (filterItem in datalist) {
//                val filterItemView = FilterItemView(context!!)
//                filterItemView.setTitle(filterItem.title)
//                filterItemView.setItemData(filterItem.items)
//                filterItemContainer?.addView(filterItemView)
//            }
//
//        }
    }




}