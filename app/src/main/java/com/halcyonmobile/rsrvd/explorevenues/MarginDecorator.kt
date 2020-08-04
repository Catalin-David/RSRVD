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
    // Default margin for a card (dp)
    private val margin: Int = 40

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