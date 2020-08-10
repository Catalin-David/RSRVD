package com.halcyonmobile.rsrvd.explorevenues.filter

import android.graphics.Color.WHITE
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.databinding.TimeStepBinding

class TimePickerAdapter : ListAdapter<Int, TimePickerAdapter.TimeStepViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeStepViewHolder =
        TimeStepViewHolder(TimeStepBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TimeStepViewHolder, position: Int) = holder.bind(getItem(position % currentList.size))

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Int>() {
            override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem
        }
    }

    inner class TimeStepViewHolder(private val binding: TimeStepBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int) {
            val hour = (item / 100 % 12)
            val minute = ((item % 100) * 60 / 100)

            binding.step.text = binding.root.context.getString(
                R.string.time,
                if (hour < 10) "0$hour" else hour,
                if (minute < 10) "0$minute" else minute,
                if (item / 100 < 12) "AM" else "PM"
            )
        }

        fun select() = binding.step.setTextColor(WHITE)

        @RequiresApi(Build.VERSION_CODES.M)
        fun unSelect() = binding.step.setTextColor(binding.root.context.getColor(R.color.gray))
    }
}
