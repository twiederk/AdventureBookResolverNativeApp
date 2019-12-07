package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entry

import android.view.View
import androidx.core.content.ContextCompat
import com.d20charactersheet.adventurebookresolver.core.domain.Visit
import com.d20charactersheet.adventurebookresolver.nativeapp.R

class ColorHelper {
    fun getColor(view: View, visit: Visit): Int = when (visit) {
        Visit.VISITED -> ContextCompat.getColor(
            view.context,
            R.color.entryVisited
        )
        Visit.UNVISITED -> ContextCompat.getColor(
            view.context,
            R.color.entryUnvisited
        )
    }

}