package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry

interface BookRenderer {

    fun render(): Pair<List<GraphEntry>, List<GraphEdge>>

}

data class GraphEntry(
    val entry: BookEntry,
    var left: Float = 0F,
    val top: Float = 0F,
    var right: Float = 0F,
    val bottom: Float = 0F,
    val current: Boolean = false
) {
    val width: Float
        get() = right - left
    val height: Float
        get() = bottom - top
}

data class GraphEdge(
    val startX: Float,
    val startY: Float,
    val endX: Float,
    val endY: Float,
    val label: String,
    val labelX: Float,
    val labelY: Float,
    val dest: BookEntry
)


