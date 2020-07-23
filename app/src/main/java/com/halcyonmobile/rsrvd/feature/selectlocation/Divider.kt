package com.halcyonmobile.rsrvd.feature.selectlocation

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.rsrvd.R

class Divider(context: Context) : RecyclerView.ItemDecoration() {
    private val drawable: Drawable = ContextCompat.getDrawable(context, R.drawable.divider)!!

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.adapter!!.itemCount

        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            child?.let {
                val params = it.layoutParams as RecyclerView.LayoutParams
                val top = it.bottom + params.bottomMargin
                val bottom = top + drawable.intrinsicHeight

                drawable.setBounds(left, top, right, bottom)
                drawable.draw(c)
            }
        }
    }
}