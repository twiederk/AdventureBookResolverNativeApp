package com.d20charactersheet.adventurebookresolver.nativeapp

import android.view.View
import android.widget.TextView
import com.d20charactersheet.adventurebookresolver.core.domain.Item
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class InventoryViewHolderTest {

    @Test
    fun setItem() {
        // Arrange
        val itemTextView: TextView = mock()
        val rootView: View = mock {
            on { findViewById<TextView>(R.id.item_text_view) } doReturn itemTextView
        }

        // Act
        InventoryViewHolder(rootView).setItem(Item("myItem"))

        // Assert
        verify(itemTextView).text = "myItem"
    }
}