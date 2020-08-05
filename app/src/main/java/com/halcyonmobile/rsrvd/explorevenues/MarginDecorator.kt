package com.halcyonmobile.rsrvd.explorevenues

import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.rsrvd.R

class MarginDecorator(
    context: Context? = null,
    private val top: Boolean? = false,
    private val right: Boolean? = false,
    private val bottom: Boolean? = false,
    private val left: Boolean? = false
) : RecyclerView.ItemDecoration() {
    // Default margin for a card (dp)
    private val margin: Int =
        context?.resources?.getDimension(R.dimen.default_margin)?.times((context.resources?.displayMetrics?.densityDpi?.div(DisplayMetrics.DENSITY_DEFAULT)!!))
            ?.toInt() ?: 0

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            if (this@MarginDecorator.top == true) {
                this.top = margin
            }
            if (this@MarginDecorator.right == true) {
                this.right = margin
            }
            if (this@MarginDecorator.bottom == true) {
                this.bottom = margin
            }
            if (this@MarginDecorator.left == true) {
                this.left = margin
            }
        }
    }
}