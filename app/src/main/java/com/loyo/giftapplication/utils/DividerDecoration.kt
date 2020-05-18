package com.loyo.giftapplication.utils

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.style.LineHeightSpan
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DividerDecoration(private val color: Int, private val dividerHeight: Int) :
    RecyclerView.ItemDecoration() {
    private val paint = Paint()


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        paint.color = color

        val count = parent.childCount
        for (i in 0 until count) {
            val view = parent.getChildAt(i)
            c.drawRect(
                view.left.toFloat(),
                (view.bottom - dividerHeight).toFloat(),
                view.right.toFloat(), (view.bottom ).toFloat(), paint
            )
        }

    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(0, 0, 0, dividerHeight)
    }
}