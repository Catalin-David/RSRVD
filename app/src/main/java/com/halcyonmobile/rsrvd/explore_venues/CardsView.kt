package com.halcyonmobile.rsrvd.explore_venues

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.rsrvd.R

class CardsView(context: Context, attributeSet: AttributeSet?) : ConstraintLayout(context) {
    private val view = inflate(context, R.layout.cards, this)
    private val title = view.findViewById<TextView>(R.id.title)
    private val holder = view.findViewById<RecyclerView>(R.id.holder)
    private val adapter = CardsAdapter {
        println(it)
    }

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
        adapter.submitList(listOf(Card(title="a"), Card(title="b"), Card(title="c")))
    }

    private fun setTitle(title: String) {
        this.title.text = title
    }
}