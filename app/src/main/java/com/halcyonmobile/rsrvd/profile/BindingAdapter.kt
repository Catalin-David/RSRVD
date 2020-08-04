package com.halcyonmobile.rsrvd.profile

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.halcyonmobile.rsrvd.R

object BindingAdapter {
    @BindingAdapter("imageUrl")
    @JvmStatic fun ImageView.setImageUrl(imageUrl: Uri?){
        Glide.with(this).asBitmap().load(imageUrl).error(R.mipmap.ic_launcher).into(this)
    }
}