package com.halcyonmobile.rsrvd.explore_venues.shared.cards

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.rsrvd.R

open class CardsView(context: Context, attributeSet: AttributeSet?) : ConstraintLayout(context) {
    private val view by lazy { inflate(context, R.layout.cards, this) }
    private val title = view.findViewById<TextView>(R.id.title)
    private val holder = view.findViewById<RecyclerView>(R.id.holder)
    val adapter = CardsAdapter { println(it) }

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

        holder.adapter = this.adapter
        holder.layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.HORIZONTAL }
    }

    private fun setTitle(title: String) {
        this.title.text = title
    }
}