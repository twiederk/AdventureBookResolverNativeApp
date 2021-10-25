package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GraphView(context: Context, attrs: AttributeSet) : View(context, attrs), KoinComponent {

    private val bookRenderer: BookRenderer by inject()
    private val touchEventHandler: GraphViewTouchEventHandler by inject()

    internal var viewportX = 0f
    internal var viewportY = 0f

    internal var touchX: Float = 0f
    internal var touchY: Float = 0f

    internal var graphCanvas = GraphCanvas(this)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec)
        )
    }

    public override fun onDraw(canvas: Canvas) {
        initialCentering()
        graphCanvas.translate(canvas, viewportX, viewportY)
        val (entries, edges) = bookRenderer.render()
        graphCanvas.render(canvas, entries, edges, bookRenderer.scale)
    }

    private fun initialCentering() {
        if (viewportX == 0f && viewportY == 0f) {
            center()
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        touchEventHandler.onTouchEvent(this, event)
        invalidate()
        return true
    }

    fun center() {
        if (width != 0 || height != 0) {
            calculateCenter(width.toFloat(), height.toFloat())
        }
    }

    internal fun calculateCenter(width: Float, height: Float) {
        val (centerX, centerY) = bookRenderer.center()
        viewportX = (width / 2) - centerX
        viewportY = (height / 2) - centerY
    }

    fun scale(scale: Float) {
        bookRenderer.scale = scale
        invalidate()
    }

}
