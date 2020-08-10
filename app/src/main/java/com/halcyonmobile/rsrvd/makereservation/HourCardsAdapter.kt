package com.halcyonmobile.rsrvd.makereservation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.rsrvd.databinding.HourItemBinding

class HourCardsAdapter(
    private val onItemClick: (Int) -> Unit
) : ListAdapter<HourUiModel, HourCardsAdapter.HourCardViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourCardViewHolder =
        HourCardViewHolder(HourItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))

    override fun onBindViewHolder(holder: HourCardViewHolder, position: Int) {
        holder.apply {
            holder.bind(getItem(position))
            itemView.setOnClickListener { onItemClick(position) }
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<HourUiModel> = object : DiffUtil.ItemCallback<HourUiModel>() {
            override fun areItemsTheSame(oldItem: HourUiModel, newItem: HourUiModel): Boolean = oldItem.isSelected == newItem.isSelected
            override fun areContentsTheSame(oldItem: HourUiModel, newItem: HourUiModel): Boolean = oldItem == newItem
        }
    }

    inner class HourCardViewHolder(
        private val binding: HourItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HourUiModel) {
            binding.hourRadioButton.text = item.hour
            binding.hourRadioButton.isChecked = item.isSelected
        }
    }

}
