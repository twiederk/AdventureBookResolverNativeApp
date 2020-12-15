package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory

import androidx.recyclerview.widget.RecyclerView
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ItemDeleteSimpleCallbackTest {

    @Test
    fun `ItemDeleteSimpleCallback deletes action`() {
        // Arrange
        val itemAdapter: ItemAdapter = mock()
        val viewHolder: RecyclerView.ViewHolder = mock {
            on { adapterPosition } doReturn 10
        }

        // Act
        ItemDeleteOnSwipeListener(
            itemAdapter
        ).onSwiped(viewHolder, 0)

        // Assert
        verify(itemAdapter).deleteItem(10)
        verify(itemAdapter).notifyItemRemoved(10)
    }

}