package com.halcyonmobile.rsrvd.feature.selectlocation

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.rsrvd.R

class Divider(private val context: Context) : RecyclerView.ItemDecoration() {
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (i in 0 until parent.adapter!!.itemCount - 1) {
            parent.getChildAt(i)?.let {
                ContextCompat.getDrawable(context, R.drawable.divider)!!.apply{
                    setBounds(
                        parent.paddingLeft,
                        it.bottom + (it.layoutParams as RecyclerView.LayoutParams).bottomMargin,
                        parent.width - parent.paddingRight,
                        it.bottom + (it.layoutParams as RecyclerView.LayoutParams).bottomMargin + intrinsicHeight)
                    draw(c)
                }
            }
        }
    }
}