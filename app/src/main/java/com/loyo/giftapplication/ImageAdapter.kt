package com.loyo.giftapplication

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.youth.banner.adapter.BannerAdapter

class ImageAdapter(datas: List<Int>) : BannerAdapter<Int, ImageAdapter.ImageViewHolder>(datas) {
    class ImageViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView)

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageViewHolder {
        val imageView = ImageView(parent!!.context)
        val param = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.FIT_END
        imageView.layoutParams = param
        return ImageViewHolder(imageView)
    }

    override fun onBindView(holder: ImageViewHolder?, data: Int?, position: Int, size: Int) {
        holder!!.imageView.setImageResource(data!!)
    }
}