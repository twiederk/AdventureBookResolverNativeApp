package com.d20charactersheet.adventurebookresolver.nativeapp

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

abstract class OnSwipeListener : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    internal var background: Drawable = ColorDrawable(Color.RED)

    override fun onMove(
        recylerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onChildDraw(
        canvas: Canvas, recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        onChildDraw(canvas, viewHolder.itemView, dX.toInt())
    }

    internal fun onChildDraw(canvas: Canvas, itemView: View, dX: Int) {

        when {
            dX > 0 -> // Swiping to the right
                background.setBounds(
                    itemView.left, itemView.top,
                    itemView.left + dX + BACKGROUND_CORNER_OFFSET,
                    itemView.bottom
                )
            dX < 0 -> // Swiping to the left
                background.setBounds(
                    itemView.right + dX - BACKGROUND_CORNER_OFFSET,
                    itemView.top, itemView.right, itemView.bottom
                )
            else -> // view is unSwiped
                background.setBounds(0, 0, 0, 0)
        }
        background.draw(canvas)
    }

    companion object {
        private const val BACKGROUND_CORNER_OFFSET = 20
    }

}