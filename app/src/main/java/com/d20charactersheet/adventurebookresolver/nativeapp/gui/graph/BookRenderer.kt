package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.LabeledEdge
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.jgrapht.Graph
import org.jgrapht.traverse.BreadthFirstIterator

class BookRenderer(private val game: Game) {

    companion object {
        private const val PADDING = 300f
        private const val HEIGHT = 250f
    }

    private var graphEntries: List<GraphEntry> = calculateGraphEntries()
    private var graphEdges: List<GraphEdge> = calculateGraphEdges(graphEntries)

    var scale: Float = 1f

    fun render(): Pair<List<GraphEntry>, List<GraphEdge>> {
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
        val left = (PADDING + (index * PADDING) + withOfEntriesToTheLeft) * scale
        val ownWidth = GraphPaint.textPaint.measureText(currentEntry.title)
        val right = left + (ownWidth * scale)

        val top = (PADDING + (depth * (PADDING + HEIGHT))) * scale
        val bottom = top + (HEIGHT * scale)

        val selected = currentEntry.id == game.book.getEntryId()

        return GraphEntry(left, top, right, bottom, currentEntry, selected)
    }

    private fun sumWidthOfEntriesToTheLeft(entries: MutableList<BookEntry>): Float {
        var totalWidth = 0F
        for (entry in entries) {
           totalWidth += GraphPaint.textPaint.measureText(entry.title)
        }
        return totalWidth
    }

    private fun sortEntriesByDepth(graph: Graph<BookEntry, LabeledEdge>, bookEntry: BookEntry)
            : MutableMap<Int, MutableList<BookEntry>> {
        val breadthFirstIterator = BreadthFirstIterator(graph, bookEntry)

        val entriesByDepth = mutableMapOf<Int, MutableList<BookEntry>>()
        for (entry in breadthFirstIterator) {
            val depth = breadthFirstIterator.getDepth(entry)
            val entriesOfDepth = entriesByDepth.getOrPut(depth) { mutableListOf() }
            entriesOfDepth.add(entry)
        }
        return entriesByDepth
    }

    private fun calculateGraphEdges(graphEntries: List<GraphEntry>): List<GraphEdge> {
        val graph = game.book.graph
        val edges = game.book.graph.edgeSet()


        val graphEdges = mutableListOf<GraphEdge>()
        val entriesMap: Map<Int, GraphEntry> = graphEntries.map { it.entry.id to it }.toMap()

        for (edge in edges) {
            val graphEdge = createGraphEdge(entriesMap, graph, edge)
            graphEdges.add(graphEdge)
        }
        return graphEdges

    }

    private fun createGraphEdge(
        entriesMap: Map<Int, GraphEntry>,
        graph: Graph<BookEntry, LabeledEdge>,
        edge: LabeledEdge
    ): GraphEdge {
        val source = entriesMap.getValue(graph.getEdgeSource(edge).id)
        val dest = entriesMap.getValue(graph.getEdgeTarget(edge).id)
        val startX = source.left + (source.width / 2)
        val startY = source.bottom
        val endX = dest.left + (dest.width / 2)
        val endY = dest.top
        val labelX = (startX + endX) / 2
        val labelY = (startY + endY) / 2

        return GraphEdge(startX, startY, endX, endY, edge.label, labelX, labelY, dest.entry)
    }

    fun center(): Pair<Float, Float> {
        val graphEntry = graphEntries.find { it.entry.id == game.book.getEntryId() }
        return graphEntry?.let {
            val centerX = it.left + (it.width / 2)
            val centerY = it.top + ((HEIGHT / 2) * scale)
            Pair(centerX, centerY)
        } ?: Pair((HEIGHT / 2) * scale, (HEIGHT / 2) * scale)
    }

    fun touch(x: Float, y: Float): BookEntry? {
        return graphEntries.find { it.left <= x && it.right >= x && it.top <= y && it.bottom >= y }?.entry
    }

}

data class GraphEntry(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float,
    val entry: BookEntry,
    val current: Boolean = false
) {
    val width: Float
        get() = right - left
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
