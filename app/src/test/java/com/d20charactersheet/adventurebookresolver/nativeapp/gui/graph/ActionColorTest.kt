package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.view.View
import com.d20charactersheet.adventurebookresolver.core.domain.Visit
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class ActionColorTest {

    @Test
    fun getColor_visited() {
        // Arrange
        val view: View = mock()
        val resourceHelper: ResourceHelper = mock()

        // Act
        ActionColor(resourceHelper).getColor(view, WayMark.NORMAL, Visit.VISITED)

        // Assert
        verify(resourceHelper).getColor(view, R.color.entryVisited)
    }

    @Test
    fun getColor_unvisited() {
        // Arrange
        val view: View = mock()
        val resourceHelper: ResourceHelper = mock()

        // Act
        ActionColor(resourceHelper).getColor(view, WayMark.NORMAL, Visit.UNVISITED)

        // Assert
        verify(resourceHelper).getColor(view, R.color.entryUnvisited)
    }

    @Test
    fun getColor_way_point() {
        // Arrange
        val view: View = mock()
        val resourceHelper: ResourceHelper = mock()

        // Act
        ActionColor(resourceHelper).getColor(view, WayMark.WAY_POINT, Visit.UNVISITED)

        // Assert
        verify(resourceHelper).getColor(view, R.color.entryWayPoint)
    }

    @Test
    fun getColor_dead_end() {
        // Arrange
        val view: View = mock()
        val resourceHelper: ResourceHelper = mock()

        // Act
        ActionColor(resourceHelper).getColor(view, WayMark.DEAD_END, Visit.UNVISITED)

        // Assert
        verify(resourceHelper).getColor(view, R.color.entryDeadEnd)
    }


}