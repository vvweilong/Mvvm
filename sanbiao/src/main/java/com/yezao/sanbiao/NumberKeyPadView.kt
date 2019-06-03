package com.yezao.sanbiao

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

class NumberKeyPadView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), View.OnClickListener {


    init {
        LayoutInflater.from(context).inflate(R.layout.number_keypad_layout, this, true)
        val gridlayout = findViewById<ViewGroup>(R.id.container)
        if (gridlayout != null) {
            for (i in 0 until gridlayout.childCount){
                gridlayout.getChildAt(i).setOnClickListener(this)
            }
        }
    }


    override fun onClick(v: View?) {
        if (listener == null) {
            return
        }
        when (v?.id) {
            R.id.text_num_0 -> {
                listener?.keyPressed(0, KeyType.NUM)
            }
            R.id.text_num_1 -> {
                listener?.keyPressed(1, KeyType.NUM)
            }
            R.id.text_num_2 -> {
                listener?.keyPressed(2, KeyType.NUM)
            }
            R.id.text_num_3 -> {
                listener?.keyPressed(3, KeyType.NUM)
            }
            R.id.text_num_4 -> {
                listener?.keyPressed(4, KeyType.NUM)
            }
            R.id.text_num_5 -> {
                listener?.keyPressed(5, KeyType.NUM)
            }
            R.id.text_num_6 -> {
                listener?.keyPressed(6, KeyType.NUM)
            }
            R.id.text_num_7 -> {
                listener?.keyPressed(7, KeyType.NUM)
            }
            R.id.text_num_8 -> {
                listener?.keyPressed(8, KeyType.NUM)
            }
            R.id.text_num_9 -> {
                listener?.keyPressed(9, KeyType.NUM)
            }
            R.id.text_sign_dot -> {
                listener?.keyPressed(-1, KeyType.DOT)
            }
            R.id.text_sign_back -> {
                listener?.keyPressed(-1, KeyType.DEL)
            }
            else -> {
            }
        }
    }


    var listener: KeyPadListener? = null

    interface KeyPadListener {
        /**
         * @param value 数字的值 0 ~9
         * @param keyType 按键类型 NUM：数值 DOT 小数点  DEL 删除键
         * */
        fun keyPressed(value: Int, keyType: KeyType)
    }

    enum class KeyType {
        NUM, DOT, DEL
    }
}