package com.basic.indiagamermall.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.basic.indiagamermall.R
import com.bumptech.glide.Glide


class EventViewPagerAdapter(var arrImages: ArrayList<String>, var ctx: Context): PagerAdapter() {

    lateinit var layoutInflater: LayoutInflater

    override fun getCount() = arrImages.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view.equals(`object`)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(ctx)
        var view: View = layoutInflater.inflate(R.layout.event_vp_card,container, false)
        var eventImg: ImageView = view.findViewById(R.id.event_vp_img)
        //eventImg.setImageResource(arrImages[position])
        Glide.with(ctx).load(arrImages[position]).into(eventImg)
        container.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView( `object`as View)
    }

}





