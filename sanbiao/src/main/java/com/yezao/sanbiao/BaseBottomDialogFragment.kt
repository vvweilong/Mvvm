package com.yezao.sanbiao


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


abstract class BaseBottomDialogFragment : DialogFragment() {

    override fun onStart() {
        super.onStart()
        val dm = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(dm)

        val targetWidht = Math.round(dm.widthPixels * getWidthPersent())
        val targetHeight = Math.round(dm.heightPixels * getHeightPersent())

        dialog.window.setLayout(targetWidht, targetHeight)
        dialog.window.setGravity(Gravity.BOTTOM)
    }


    fun getHeightPersent(): Float {
        return 0.9f
    }

    fun getWidthPersent(): Float {
        return 1f
    }
}
