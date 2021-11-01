package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GraphView(context: Context, attrs: AttributeSet) : View(context, attrs), KoinComponent {

    private val touchEventHandler: GraphViewTouchEventHandler by inject()

    internal var viewportX = 0F
    internal var viewportY = 0F

    internal var touchX: Float = 0F
    internal var touchY: Float = 0F

    internal var graphCanvas = GraphCanvas(this)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec)
        )
    }

    public override fun onDraw(canvas: Canvas) {
        initialCenteringOnCurrentBookEntry()
        graphCanvas.translate(canvas, viewportX, viewportY)
        graphCanvas.render(canvas)
    }

    private fun initialCenteringOnCurrentBookEntry() {
        if (viewportX == 0f && viewportY == 0f) {
            guardedCenteringOnCurrentBookEntry()
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        touchEventHandler.onTouchEvent(this, graphCanvas, event)
        invalidate()
        return true
    }

    fun guardedCenteringOnCurrentBookEntry() {
        if (width != 0 || height != 0) {
            centerOnCurrentBookEntry(width.toFloat(), height.toFloat())
        }
    }

    internal fun centerOnCurrentBookEntry(viewportWidth: Float, viewportHeight: Float) {
        val (centerX, centerY) = graphCanvas.getCenterOfCurrentGraphEntry()
        viewportX = (viewportWidth / 2) - centerX
        viewportY = (viewportHeight / 2) - centerY
    }

    fun scale(scale: Float) {
        graphCanvas.scale = scale
        invalidate()
    }

}
