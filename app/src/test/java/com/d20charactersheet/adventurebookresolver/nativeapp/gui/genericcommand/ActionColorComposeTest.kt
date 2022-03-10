package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import com.d20charactersheet.adventurebookresolver.core.domain.Visit
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.EntryDeadEndColor
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.EntryUnvisitedColor
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.EntryVisitedColor
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.EntryWayPointColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ActionColorComposeTest {

    @Test
    fun getColor_visited() {

        // Act
        val color = ActionColorCompose().getColor(WayMark.NORMAL, Visit.VISITED)

        // Assert
        assertThat(color).isEqualTo(EntryVisitedColor)
    }

    @Test
    fun getColor_unvisited() {

        // Act
        val color = ActionColorCompose().getColor(WayMark.NORMAL, Visit.UNVISITED)

        // Assert
        assertThat(color).isEqualTo(EntryUnvisitedColor)
    }

    @Test
    fun getColor_way_point() {

        // Act
        val color = ActionColorCompose().getColor(WayMark.WAY_POINT, Visit.UNVISITED)

        // Assert
        assertThat(color).isEqualTo(EntryWayPointColor)
    }

    @Test
    fun getColor_dead_end() {

        // Act
        val color = ActionColorCompose().getColor(WayMark.DEAD_END, Visit.UNVISITED)

        // Assert
        assertThat(color).isEqualTo(EntryDeadEndColor)
    }


}