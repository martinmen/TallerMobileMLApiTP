package com.example.tallermobiletpmlapi.adapters

import android.content.Context
import android.view.View
import androidx.viewpager.widget.PagerAdapter

class SliderAdapter : PagerAdapter {
    var context: Context
    var images: ArrayList<Int>

    constructor(context: Context, images: ArrayList<Int>) : super() {
        this.context = context
        this.images = images
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int {
        return images.size
    }

}