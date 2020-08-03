package com.halcyonmobile.rsrvd.explorevenues

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginDecorator(
    private val top: Boolean? = false,
    private val right: Boolean? = false,
    private val bottom: Boolean? = false,
    private val left: Boolean? = false
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            if (this@MarginDecorator.top == true) {
                this.top = DEFAULT_MARGIN
            }
            if (this@MarginDecorator.right == true) {
                this.right = DEFAULT_MARGIN
            }
            if (this@MarginDecorator.bottom == true) {
                this.bottom = DEFAULT_MARGIN
            }
            if (this@MarginDecorator.left == true) {
                this.left = DEFAULT_MARGIN
            }
        }
    }

    companion object {
        const val DEFAULT_MARGIN = 35
    }
}