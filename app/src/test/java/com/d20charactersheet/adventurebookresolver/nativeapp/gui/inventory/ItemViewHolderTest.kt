package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory

import android.view.View
import android.widget.TextView
import com.d20charactersheet.adventurebookresolver.core.domain.Item
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ItemViewHolderTest {

    @Test
    fun setItem() {
        // Arrange
        val itemTextView: TextView = mock()
        val rootView: View = mock {
            on { findViewById<TextView>(R.id.item_text_view) } doReturn itemTextView
        }

        // Act
        ItemViewHolder(rootView).setItem(Item("myItem"))

        // Assert
        verify(itemTextView).text = "myItem"
    }
}