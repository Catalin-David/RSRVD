package com.halcyonmobile.rsrvd.explorevenues

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.rsrvd.R

class MarginDecorator(
    private val top: Boolean? = false,
    private val right: Boolean? = false,
    private val bottom: Boolean? = false,
    private val left: Boolean? = false
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val marginDefault: Int = view.context.resources.getDimensionPixelSize(R.dimen.default_padding)
        val marginLong = view.context.resources.getDimensionPixelSize(R.dimen.long_padding)

        with(outRect) {
            if (this@MarginDecorator.top == true) {
                this.top = marginDefault
            }
            if (this@MarginDecorator.right == true) {
                this.right = marginDefault

                // List expands to the right --> first item is indented
                if (parent.childCount == 1) {
                    this.left = marginLong
                }
            }
            if (this@MarginDecorator.bottom == true) {
                this.bottom = marginDefault
            }
            if (this@MarginDecorator.left == true) {
                this.left = marginDefault
            }
        }
    }
}