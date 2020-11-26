package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.view.View
import com.d20charactersheet.adventurebookresolver.core.domain.Visit
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import com.d20charactersheet.adventurebookresolver.nativeapp.R

class ActionColor(private val resourceHelper: ResourceHelper = ResourceHelper()) {

    fun getColor(view: View, wayMark: WayMark, visit: Visit) = when (wayMark) {
        WayMark.WAY_POINT -> resourceHelper.getColor(view, R.color.entryWayPoint)
        WayMark.DEAD_END -> resourceHelper.getColor(view, R.color.entryDeadEnd)
        WayMark.NORMAL -> when (visit) {
            Visit.VISITED -> resourceHelper.getColor(view, R.color.entryVisited)
            Visit.UNVISITED -> resourceHelper.getColor(view, R.color.entryUnvisited)
        }
    }

}