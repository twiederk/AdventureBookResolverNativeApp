package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game

class DepthBookRenderer(game: Game) : AbstractBookRenderer(game) {

    companion object {
        private const val PADDING = 300f
        private const val HEIGHT = 250f
    }

    private var graphEntries: List<GraphEntry> = calculateGraphEntries()
    private var graphEdges: List<GraphEdge> = calculateGraphEdges(graphEntries)

    override fun render(): Pair<List<GraphEntry>, List<GraphEdge>> {
        graphEntries = calculateGraphEntries()
        graphEdges = calculateGraphEdges(graphEntries)
        return Pair(graphEntries, graphEdges)
    }

    private fun calculateGraphEntries(): List<GraphEntry> {
        val entriesByDepth = sortEntriesByDepth(game.book.graph, game.book.getBookEntry(1))
        return calculateGraphEntries(entriesByDepth)
    }

    private fun calculateGraphEntries(entriesByDepth: MutableMap<Int, MutableList<BookEntry>>): MutableList<GraphEntry> {
        val graphEntries = mutableListOf<GraphEntry>()
        for (depth in entriesByDepth.keys) {
            val entries = entriesByDepth[depth]
            entries?.forEachIndexed { index, currentEntry ->
                val graphEntry = createGraphEntry(index, depth, currentEntry, entries)
                graphEntries.add(graphEntry)
            }
        }
        return graphEntries
    }

    private fun createGraphEntry(
        index: Int,
        depth: Int,
        currentEntry: BookEntry,
        entries: MutableList<BookEntry>
    ): GraphEntry {
        val withOfEntriesToTheLeft = sumWidthOfEntriesToTheLeft(entries.subList(0, index))
        val left = PADDING + (index * PADDING) + withOfEntriesToTheLeft
        val ownWidth = GraphPaint.textPaint.measureText(currentEntry.title)
        val right = left + ownWidth

        val top = PADDING + (depth * (PADDING + HEIGHT))
        val bottom = top + HEIGHT

        val selected = currentEntry.id == game.book.getEntryId()

        return GraphEntry(entry = currentEntry, left = left, top = top, right = right, bottom = bottom, current = selected)
    }

    private fun sumWidthOfEntriesToTheLeft(entries: MutableList<BookEntry>): Float {
        var totalWidth = 0F
        for (entry in entries) {
           totalWidth += GraphPaint.textPaint.measureText(entry.title)
        }
        return totalWidth
    }

}

