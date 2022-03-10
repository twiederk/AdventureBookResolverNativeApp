package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import androidx.compose.ui.graphics.Color
import com.d20charactersheet.adventurebookresolver.core.domain.Visit
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.EntryDeadEndColor
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.EntryUnvisitedColor
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.EntryVisitedColor
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.EntryWayPointColor

class ActionColorCompose {

    fun getColor(wayMark: WayMark, visit: Visit): Color = when (wayMark) {
        WayMark.WAY_POINT -> EntryWayPointColor
        WayMark.DEAD_END -> EntryDeadEndColor
        WayMark.NORMAL -> when (visit) {
            Visit.VISITED -> EntryVisitedColor
            Visit.UNVISITED -> EntryUnvisitedColor
        }
    }

}
