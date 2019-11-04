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
        val inventoryAdapter: InventoryAdapter = mock()
        val viewHolder: RecyclerView.ViewHolder = mock {
            on { adapterPosition } doReturn 10
        }

        // Act
        InventoryDeleteOnSwipeListener(inventoryAdapter).onSwiped(viewHolder, 0)

        // Assert
        verify(inventoryAdapter).deleteItem(10)
        verify(inventoryAdapter).notifyItemRemoved(10)
    }

}