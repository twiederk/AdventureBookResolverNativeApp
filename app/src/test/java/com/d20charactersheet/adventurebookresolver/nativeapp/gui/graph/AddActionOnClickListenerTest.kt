package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.view.View
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class AddActionOnClickListenerTest {

    @Test
    fun onClick() {
        // Arrange
        val view: View = mock {
            on { context } doReturn mock()
        }
        val actionAddDialog: ActionAddDialog = mock()

        // Act
        AddActionOnClickListener(actionAddDialog).onClick(view)

        // Assert
        verify(actionAddDialog).show(any())

    }

}