package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entry

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.koin.core.KoinComponent
import org.koin.core.inject

class GraphView(context: Context, attrs: AttributeSet) : View(context, attrs), KoinComponent {

    private val game: Game by inject()
    private val bookRenderer: BookRenderer by inject()

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
        graphCanvas.render(canvas, entries, edges)
    }

    private fun initialCentering() {
        if (viewportX == 0f && viewportY == 0f) {
            center()
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchX = event.x - viewportX
                touchY = event.y - viewportY
                val bookEntry = bookRenderer.touch(touchX, touchY)
                bookEntry?.let {
                    game.touch(bookEntry)
                    center()
                }
            }

            MotionEvent.ACTION_MOVE -> {
                viewportX = event.x - touchX
                viewportY = event.y - touchY

            }

        }

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
