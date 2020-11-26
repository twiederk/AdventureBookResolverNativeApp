package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.view.View
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GraphPanelTest {

    @Test
    fun `create graph panel`() {
        // Arrange
        val graphView: GraphView = mock()
        val rootView: View = mock {
            on { findViewById<GraphView>(R.id.graph_view) } doReturn graphView
        }

        val bookRenderer: BookRenderer = mock()
        val underTest = GraphPanel(bookRenderer)

        // Act
        underTest.create(rootView)

        // Assert
        assertThat(underTest.bookRenderer).isEqualTo(bookRenderer)
        assertThat(underTest.graphView).isEqualTo(graphView)
    }

    @Test
    fun `update graph panel`() {
        // Arrange
        val bookRenderer: BookRenderer = mock {
            on { center() } doReturn Pair(10f, 20f)
        }
        val underTest = GraphPanel(bookRenderer)

        val graphView: GraphView = mock()
        underTest.graphView = graphView

        // Act
        underTest.update()

        // Assert
        verify(graphView).center()
        verify(graphView).invalidate()
    }

}