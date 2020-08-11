package com.halcyonmobile.rsrvd.reservation

import androidx.recyclerview.widget.DiffUtil
import com.halcyonmobile.rsrvd.core.reservation.dto.ReservationDto

class ReservationDiffCallback : DiffUtil.ItemCallback<ReservationDto>() {
    override fun areItemsTheSame(oldItem: ReservationDto, newItem: ReservationDto) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ReservationDto, newItem: ReservationDto) = oldItem == newItem
}