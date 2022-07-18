package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen

import android.content.Context
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class FileHelperTest {

    private val context: Context = mock()
    private val fileHelper = FileHelper(context)

    @Test
    fun should_return_list_of_internal_files() {
        // arrange
        whenever(context.fileList()).thenReturn(arrayOf("file 1", "file 2"))

        // act
        val internalFileNames = fileHelper.getInternalFileNames()

        // assert
        assertThat(internalFileNames).containsExactly("file 1", "file 2")
    }

    @Test
    fun should_return_empty_list_when_context_returns_null() {

        // act
        val internalFileNames = fileHelper.getInternalFileNames()

        // assert
        assertThat(internalFileNames).isEmpty()
    }

}