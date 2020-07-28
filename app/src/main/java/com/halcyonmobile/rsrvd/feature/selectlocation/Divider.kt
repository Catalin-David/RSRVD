package com.halcyonmobile.rsrvd.feature.selectlocation

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.rsrvd.R

class Divider(private val context: Context) : RecyclerView.ItemDecoration() {
    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.children.forEach {
            ContextCompat.getDrawable(context, R.drawable.divider)?.apply {
                setBounds(
                    parent.paddingLeft,
                    it.bottom + (it.layoutParams as RecyclerView.LayoutParams).bottomMargin,
                    parent.width - parent.paddingRight,
                    it.bottom + (it.layoutParams as RecyclerView.LayoutParams).bottomMargin + intrinsicHeight
                )
                draw(canvas)
            }
        }
    }
}
