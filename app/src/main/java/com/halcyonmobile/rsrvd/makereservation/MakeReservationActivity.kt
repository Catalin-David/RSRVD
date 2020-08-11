package com.halcyonmobile.rsrvd.makereservation

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.halcyonmobile.rsrvd.R
import com.halcyonmobile.rsrvd.core.shared.Interests
import com.halcyonmobile.rsrvd.databinding.ActivityMakeReservationBinding

class MakeReservationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMakeReservationBinding
    private lateinit var viewModel: MakeReservationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_make_reservation)
        binding.lifecycleOwner = this
        binding.dataMap = Interests.values().toMutableList()

        viewModel = ViewModelProviders.of(this).get(MakeReservationViewModel::class.java)

        val hourCardsAdapter = HourCardsAdapter(onItemClick = {
            Log.d(ContentValues.TAG, "$it hey")
            viewModel.resetPosition(it)
        })

        setUpObservers(hourCardsAdapter)
        setUpLists(hourCardsAdapter)

    }

    private fun setUpObservers(hourCardsAdapter: HourCardsAdapter) {
        viewModel.apply {
            hourCards.observe(this@MakeReservationActivity) { hourCardsAdapter.submitList(it) }
        }
    }

    private fun setUpLists(hourCardsAdapter: HourCardsAdapter) {
        setUpRecyclerView(
            binding.recyclerView,
            LinearLayoutManager(this).apply { orientation = LinearLayoutManager.HORIZONTAL },
            hourCardsAdapter
        )
    }

    private fun setUpRecyclerView(
        recyclerView: RecyclerView,
        linearLayoutManager: LinearLayoutManager,
        listAdapter: HourCardsAdapter
    ){
      recyclerView.apply {
          setHasFixedSize(false)
          layoutManager = linearLayoutManager
          adapter = listAdapter
      }
    }

}
