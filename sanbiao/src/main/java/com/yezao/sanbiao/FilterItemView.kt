package com.yezao.sanbiao

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView

class FilterItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {


    lateinit var titleTextView: TextView
    lateinit var itemContainer: GridLayout


    var selectMode = SelectMode.SINGLE

    enum class SelectMode {
        SINGLE, MULTY
    }

    init {
        orientation = VERTICAL
        titleTextView = TextView(context)
        itemContainer = GridLayout(context)
        initView()
    }

    private fun initView() {
        val titleLayoutParams =
            LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        addView(titleTextView, titleLayoutParams)
        val itemContainerParams =
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        addView(itemContainer, itemContainerParams)
        itemContainer.orientation = HORIZONTAL
        itemContainer.columnCount = 4
        itemContainer.useDefaultMargins=true

        itemSelectorRes = R.drawable.item_choose_bg
        itemTextColorsRes = R.color.colors


        setItemData( arrayListOf(
            FilterBottomSheetDialog.FilterChose("1", "1"),
//            FilterBottomSheetDialog.FilterChose("2", "2"),
//            FilterBottomSheetDialog.FilterChose("3", "3"),
//            FilterBottomSheetDialog.FilterChose("4", "4"),
            FilterBottomSheetDialog.FilterChose("5", "5")
        ))
    }

    open fun setTitle(title: String) {
        titleTextView.text = title
    }

    private var dataList: ArrayList<ItemValueInterface>? = null
    open fun setItemData(data: ArrayList<ItemValueInterface>) {
        dataList = data
        for (datum in data) {
            addItem(datum.getItemText())
        }
    }


    private var itemSelectorRes = 0
    open fun setItemSelector(selectorRes: Int) {
        itemSelectorRes = selectorRes
        updateRes()
    }

    private var itemTextColorsRes = 0
    open fun setItemTextColors(colorsRes: Int) {
        itemTextColorsRes = colorsRes
        updateRes()
    }

    private fun updateRes() {
        itemList.forEach {
            it.setTextColor(itemTextColorsRes)
            it.setBackgroundResource(itemSelectorRes)
        }
    }


     fun addItem(itemStr: String) {
        val item = TextView(context)
        item.setOnClickListener(this)
        item.text = itemStr
        item.setBackgroundResource(itemSelectorRes)
        val itemParams = GridLayout.LayoutParams()
        itemParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1,1f)
        itemParams.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1)
        itemParams.setGravity(Gravity.CENTER)

        //确定 行 列
        itemContainer.addView(item, itemParams)
    }

    //
    val itemList = ArrayList<TextView>()

    override fun onClick(v: View?) {
        //如果设定了 单选模式 清除其他item的选择状态
        if (selectMode == SelectMode.SINGLE) {
            itemList.forEach { v ->
                v.isSelected = false
            }
        } else if (selectMode == SelectMode.MULTY) {

        }
        v?.isSelected = v?.isSelected?.not() ?: false
        itemSelectListener?.onItemSelected(itemList.indexOf(v), v?.isSelected ?: false)
    }


    private var itemSelectListener: ItemSelectListener? = null

    interface ItemSelectListener {
        fun onItemSelected(index: Int, selected: Boolean)
    }


    interface ItemValueInterface {
        fun getItemText(): String
    }


}