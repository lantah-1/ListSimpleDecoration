package com.lamandys.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by @Author(lamandys) on 2020/7/17 3:17 PM.
 */
class StickyDecoration private constructor() : RecyclerView.ItemDecoration() {

    private var stickyItemHeight = 80
    private var stickyMarginLeft = 50f
    private var stickyHoverBuilder: StickyHoverBuilder? = null

    private var textSize = 14
    private var textColor: Int = Color.WHITE

    private var hoverDecorationBackground = Color.WHITE
    private var hoverItemDecorationPaint = Paint()

    class Builder {
        private val stickyDecoration = StickyDecoration()

        fun stickyItemHeight(height: Int): Builder {
            stickyDecoration.stickyItemHeight = height
            return this
        }

        fun withStickyHoverBuilder(builder: StickyHoverBuilder): Builder {
            stickyDecoration.stickyHoverBuilder = builder
            return this
        }

        fun stickyTextSize(sizeSp: Int): Builder {
            stickyDecoration.textSize = sizeSp
            return this
        }

        fun stickyTextColor(color: Int): Builder {
            stickyDecoration.textColor = color
            return this
        }

        fun stickyItemBackgroundColor(color: Int): Builder {
            stickyDecoration.hoverDecorationBackground = color
            return this
        }

        fun stickTextMarginLeft(margin: Float): Builder {
            stickyDecoration.stickyMarginLeft = margin
            return this
        }

        @Throws
        fun build(): StickyDecoration {
            if (stickyDecoration.stickyHoverBuilder == null) {
                throw RuntimeException("fun withStickyHoverBuilder() must be call")
            }
            return stickyDecoration
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val isHover = stickyHoverBuilder?.isFirstItemInGroup(position) ?: false
        if (isHover) {
            outRect.top = stickyItemHeight
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val itemCount = parent.adapter?.itemCount ?: 0
        if (itemCount == 0) return
        for (i in 0..itemCount) {
            val view = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(view)
            if (position < 0) return
            val isFirstItemInGroup = stickyHoverBuilder?.isFirstItemInGroup(position) ?: false
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight
            // 第一个不用绘制，留给下面处理
            if (isFirstItemInGroup && i != 0) {
                val top = view.top - stickyItemHeight
                val bottom = view.top
                hoverItemDecorationPaint.color = hoverDecorationBackground
                c.drawRect(
                    left.toFloat(),
                    top.toFloat(),
                    right.toFloat(),
                    bottom.toFloat(),
                    hoverItemDecorationPaint
                )
                val name = stickyHoverBuilder?.groupName(position) ?: return

                hoverItemDecorationPaint.apply {
                    textSize = sp2px(parent.context, this@StickyDecoration.textSize).toFloat()
                    color = textColor
                    textAlign = Paint.Align.LEFT
                    isAntiAlias = true
                }
                val fontMetrics = hoverItemDecorationPaint.fontMetrics
                c.drawText(
                    name,
                    stickyMarginLeft,
                    view.top - stickyItemHeight / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent,
                    hoverItemDecorationPaint
                )
            } else {
                if (i == 0) {
                    var top = parent.paddingTop
                    val indexView = findLastItemIndexView(itemCount,parent)
                    if (indexView != null) {
                        val suggestTop = indexView.bottom - stickyItemHeight
                        if (suggestTop < 0) {
                            top = suggestTop
                        }
                    }
                    val bottom = top + stickyItemHeight

                    hoverItemDecorationPaint.color = hoverDecorationBackground
                    c.drawRect(
                        left.toFloat(),
                        top.toFloat(),
                        right.toFloat(),
                        bottom.toFloat(),
                        hoverItemDecorationPaint
                    )
                    val name = stickyHoverBuilder?.groupName(position) ?: return

                    hoverItemDecorationPaint.apply {
                        textSize = sp2px(parent.context, this@StickyDecoration.textSize).toFloat()
                        color = textColor
                        textAlign = Paint.Align.LEFT
                        isAntiAlias = true
                    }
                    val fontMetrics = hoverItemDecorationPaint.fontMetrics
                    // text的baseline计算方式为：原点在（0,0）上时，item高度的一半加上(fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent
                    // 原点在baseLine线上时，为(fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent
                    // 理所当然，当原点像y轴上时，需要把y轴的偏移量计算上
                    c.drawText(
                        name,
                        stickyMarginLeft,
                        top + (bottom - top) / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent,
                        hoverItemDecorationPaint
                    )
                }
            }
        }
    }

    private fun findLastItemIndexView(count: Int, recyclerView: RecyclerView): View? {
        for (i in 0..count) {
            val view = recyclerView.getChildAt(i)
            val position = recyclerView.getChildAdapterPosition(view)
            if (position < 0) return null
            val firstItemName = stickyHoverBuilder?.groupName(position)
            val secondItemName = stickyHoverBuilder?.groupName(position + 1)
            if (firstItemName != secondItemName) {
                return view
            }
        }
        return null
    }

    private fun sp2px(context: Context, spValue: Int): Int {
        val fontScale: Float = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }
}