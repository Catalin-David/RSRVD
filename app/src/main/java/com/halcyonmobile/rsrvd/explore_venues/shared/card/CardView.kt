package com.halcyonmobile.rsrvd.explore_venues.shared.card

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.observe
import com.halcyonmobile.rsrvd.databinding.CardBinding.*

class CardView(context: Context) : ConstraintLayout(context) {
    private val binding = inflate(LayoutInflater.from(context))
    val viewModel = CardViewModel()

    init {
        // Adding the right margin (list element separator)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.setMargins(0, 0, 20, 0)
        this.layoutParams = params

        binding.lifecycleOwner?.let {
            viewModel.getTitle().observe(it) { newTitle -> binding.titleTextView.text = newTitle }
        }
    }
}