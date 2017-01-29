package com.yopachara.catradiod.ui.main

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.yopachara.catradiod.extensions.loadImage

@BindingAdapter("android:src")
fun setImageBinding(view: ImageView, url: String){
    view.loadImage(url)
}