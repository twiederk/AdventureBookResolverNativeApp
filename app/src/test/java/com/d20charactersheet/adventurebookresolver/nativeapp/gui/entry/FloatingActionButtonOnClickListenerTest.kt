package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entry

import android.view.View
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class FloatingActionButtonOnClickListenerTest {

    @Test
    fun onClick() {
        // Arrange
        val view: View = mock {
            on { context } doReturn mock()
        }
        val actionAddDialog: ActionAddDialog = mock()

        // Act
        FloatingActionButtonOnClickListener(actionAddDialog).onClick(view)

        // Assert
        verify(actionAddDialog).show(any())

    }

}