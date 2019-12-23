package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entry

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import org.koin.core.KoinComponent
import org.koin.core.inject

class GraphView(context: Context, attrs: AttributeSet) : View(context, attrs), KoinComponent {

    private val bookRenderer: BookRenderer by inject()

    internal var viewportX = 0f
    internal var viewportY = 0f

    internal var actionStartX: Float = 0f
    internal var actionStartY: Float = 0f

    internal var graphCanvas = GraphCanvas(this)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec)
        )
    }

    public override fun onDraw(canvas: Canvas) {
        graphCanvas.translate(canvas, viewportX, viewportY)
        val (entries, edges) = bookRenderer.render()
        graphCanvas.render(canvas, entries, edges)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                actionStartX = event.x - viewportX
                actionStartY = event.y - viewportY
            }

            MotionEvent.ACTION_MOVE -> {
                viewportX = event.x - actionStartX
                viewportY = event.y - actionStartY

            }

        }

        invalidate()
        return true
    }

    fun center(centerX: Float, centerY: Float) {
        viewportX = (width / 2) - centerX
        viewportY = (height / 2) - centerY
    }

}

data class GraphEntry(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float,
    val entry: BookEntry,
    val current: Boolean = false
)

data class GraphEdge(
    val startX: Float,
    val startY: Float,
    val endX: Float,
    val endY: Float,
    val label: String,
    val dest: BookEntry
)
