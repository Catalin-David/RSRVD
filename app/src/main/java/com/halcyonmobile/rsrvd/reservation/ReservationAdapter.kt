package com.halcyonmobile.rsrvd.reservation

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.core.reservation.dto.ReservationDto
import com.halcyonmobile.rsrvd.core.reservation.model.ReservationState
import com.halcyonmobile.rsrvd.databinding.ListItemReservationBinding

class ReservationAdapter(private val onClickCallback: (reservationId: String) -> Unit) :
    ListAdapter<ReservationDto, ReservationAdapter.ViewHolder>(ReservationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ListItemReservationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ), onClickCallback
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position), position, itemCount)

    class ViewHolder(private val binding: ListItemReservationBinding, private val onClickCallback: (reservationId: String) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reservation: ReservationDto, position: Int, size: Int) {
            binding.reservation = reservation
            if (reservation.state == ReservationState.CANCELLED) {
                ImageViewCompat.setImageTintList(binding.statusImage, ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.error)))
                binding.textViewReservationStatus.setTextColor(ContextCompat.getColor(binding.root.context, android.R.color.white))
            }
            if (position + 1 == size) {
                binding.separator.visibility = View.GONE
            }
            binding.root.setOnClickListener {
                onClickCallback(reservation.id)
            }
            binding.executePendingBindings()
        }
    }
}