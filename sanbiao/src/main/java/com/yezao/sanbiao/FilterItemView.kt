package com.yezao.sanbiao

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView

class FilterItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    lateinit var titleTextView:TextView
    lateinit var itemContainer:GridLayout


    init {
        orientation= VERTICAL
        titleTextView = TextView(context)
        itemContainer = GridLayout(context)
        initView()
    }

    private fun initView(){
        val titleLayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        addView(titleTextView,titleLayoutParams)
        val itemContainerParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        addView(itemContainer,itemContainerParams)
    }

    open fun setTitle(title:String){
        titleTextView.text = title
    }

    open fun setItemData(data:ArrayList<String>){
        for (datum in data) {
            addItem(datum)
        }
    }

    open fun addItem(itemStr:String){
        val item = TextView(context)
        item.text = itemStr
        val itemParams = GridLayout.LayoutParams()
        //确定 行 列
        itemContainer.addView(item,itemParams)
    }







}