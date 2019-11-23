package com.d20charactersheet.adventurebookresolver.nativeapp

import androidx.recyclerview.widget.RecyclerView
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class ItemDeleteSimpleCallbackTest {

    @Test
    fun `ItemDeleteSimpleCallback deletes action`() {
        // Arrange
        val itemAdapter: ItemAdapter = mock()
        val viewHolder: RecyclerView.ViewHolder = mock {
            on { adapterPosition } doReturn 10
        }

        // Act
        ItemDeleteOnSwipeListener(itemAdapter).onSwiped(viewHolder, 0)

        // Assert
        verify(itemAdapter).deleteItem(10)
        verify(itemAdapter).notifyItemRemoved(10)
    }

}