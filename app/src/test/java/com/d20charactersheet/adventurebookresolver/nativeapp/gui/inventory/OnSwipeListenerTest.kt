package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.assertj.core.api.Assertions
import org.junit.Test

class OnSwipeListenerTest {

    @Test
    fun `SwipeSimpleCallback move does nothing`() {
        // Act
        val result = DummyOnSwipeListener()
            .onMove(mock(), mock(), mock())

        // Assert
        Assertions.assertThat(result).isFalse()
    }

    @Test
    fun `SwipeSimpleCallback onChildDraw swipe right`() {
        // Arrange
        val canvas: Canvas = mock()
        val itemView: View = mock {
            on { left } doReturn 10
            on { top } doReturn 20
            on { right } doReturn 30
            on { bottom } doReturn 40
        }
        val underTest =
            DummyOnSwipeListener()
        val background: Drawable = mock()
        underTest.background = background

        // Act
        underTest.onChildDraw(canvas, itemView, 10)

        // Assert
        verify(background).setBounds(10, 20, 40, 40)
        verify(background).draw(canvas)
        verifyNoMoreInteractions(background)
    }

    @Test
    fun `SwipeSimpleCallback onChildDraw swipe left`() {
        // Arrange
        val canvas: Canvas = mock()
        val itemView: View = mock {
            on { left } doReturn 10
            on { top } doReturn 20
            on { right } doReturn 30
            on { bottom } doReturn 40
        }
        val underTest =
            DummyOnSwipeListener()
        val background: Drawable = mock()
        underTest.background = background

        // Act
        underTest.onChildDraw(canvas, itemView, -10)

        // Assert
        verify(background).setBounds(0, 20, 30, 40)
        verify(background).draw(canvas)
        verifyNoMoreInteractions(background)
    }

    @Test
    fun `SwipeSimpleCallback onChildDraw swipe stopped`() {
        // Arrange
        val canvas: Canvas = mock()
        val itemView: View = mock()
        val underTest =
            DummyOnSwipeListener()
        val background: Drawable = mock()
        underTest.background = background

        // Act
        underTest.onChildDraw(canvas, itemView, 0)

        // Assert
        verify(background).setBounds(0, 0, 0, 0)
        verify(background).draw(canvas)
        verifyNoMoreInteractions(background)
    }

}

class DummyOnSwipeListener : OnSwipeListener() {

    override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
        // nothing to do
    }

}