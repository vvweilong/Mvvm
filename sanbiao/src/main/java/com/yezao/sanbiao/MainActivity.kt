package com.yezao.sanbiao

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.support.transition.Transition
import android.support.transition.TransitionManager
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    open fun show(v:View){
       val dialog= FilterDialogFragment()

        val filterItemList = ArrayList<FilterBottomSheetDialog.FilterItem>()


        for (i in 0 until 10){
            val filterItem =
                FilterBottomSheetDialog.FilterItem("title $i", arrayListOf("1", "1", "1", "1", "1", "1", "1", "1"))
            filterItemList.add(filterItem)
        }


        dialog.setFilterItems(filterItemList)

        dialog.show(supportFragmentManager,"tag")
    }




}
