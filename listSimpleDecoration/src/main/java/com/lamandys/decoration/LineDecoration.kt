package com.lamandys.decoration

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by @Author(lamandys) on 2020/7/17 3:17 PM.
 */
class LineDecoration private constructor() : RecyclerView.ItemDecoration() {

    private var lineHeight = 2
    private var lineMarginLeft = 0f
    private var lineMarginRight = 0f

    private var lineBackgroundColor = Color.GRAY
    private var lineBackgroundPaint = Paint()

    companion object {
        fun default(): LineDecoration {
            return Builder().build()
        }
    }

    class Builder {
        private val lineDecoration = LineDecoration()

        fun lineHeight(height: Int): Builder {
            lineDecoration.lineHeight = height
            return this
        }

        fun lineMarginLeft(marginLeft: Float): Builder {
            lineDecoration.lineMarginLeft = marginLeft
            return this
        }

        fun lineMarginRight(marginRight: Float): Builder {
            lineDecoration.lineMarginRight = marginRight
            return this
        }

        fun lineBackgroundColor(color: Int): Builder {
            lineDecoration.lineBackgroundColor = color
            return this
        }

        @Throws
        fun build(): LineDecoration {
            lineDecoration.lineBackgroundPaint.apply {
                isAntiAlias = true
                color = lineDecoration.lineBackgroundColor
            }
            return lineDecoration
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = lineHeight
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (i in 0..(parent.adapter?.itemCount ?: 0)) {
            val view = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(view)
            if (position < 0) return

            val left = parent.paddingLeft + lineMarginLeft
            val right = parent.width - parent.paddingRight - lineMarginRight
            lineBackgroundPaint.color = lineBackgroundColor
            c.drawRect(left, view.top.toFloat(), right, lineHeight.toFloat(), lineBackgroundPaint)

            val background =
                if (view.background is ColorDrawable) {
                    (view.background as ColorDrawable).color
                } else {
                    null
                }
            if (background != null) {
                lineBackgroundPaint.color = background
                c.drawRect(0f, view.top.toFloat(), left, lineHeight.toFloat(), lineBackgroundPaint)
                c.drawRect(
                    right,
                    view.top.toFloat(),
                    (parent.width - parent.paddingRight).toFloat(),
                    lineHeight.toFloat(),
                    lineBackgroundPaint
                )
            }
        }
    }
}