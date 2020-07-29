package com.halcyonmobile.rsrvd.explore.recentlyvisited

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.rsrvd.R

class CardsView(context: Context, attributeSet: AttributeSet?) : ConstraintLayout(context) {
    private val view = inflate(context, R.layout.cards, this)
    private val title = view.findViewById<TextView>(R.id.title)
    private val holder = view.findViewById<RecyclerView>(R.id.holder)

    init {
        if (attributeSet != null) {
            context.theme.obtainStyledAttributes(attributeSet, R.styleable.CardsView, 0, 0).apply {
                try {
                    getString(R.styleable.CardsView_title)?.let { setTitle(it) }
                } finally {
                    recycle()
                }
            }
        }

    }

    fun setTitle(title: String) {
        this.title.text = title
    }
}