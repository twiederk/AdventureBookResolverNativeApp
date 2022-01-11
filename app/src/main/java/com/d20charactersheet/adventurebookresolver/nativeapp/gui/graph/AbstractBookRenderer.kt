package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.LabeledEdge
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.jgrapht.Graph
import org.jgrapht.traverse.BreadthFirstIterator

abstract class AbstractBookRenderer(protected val game: Game) : BookRenderer {

    companion object {
        const val OFFSET_LABEL_Y = 100
    }

    override val currentEntryId: Int
        get() = game.book.getEntryId()

    fun sortEntriesByDepth(graph: Graph<BookEntry, LabeledEdge>, bookEntry: BookEntry)
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

    protected fun calculateGraphEdges(graphEntries: List<GraphEntry>): List<GraphEdge> {
        val graph = game.book.graph
        val edges = game.book.graph.edgeSet()


        val graphEdges = mutableListOf<GraphEdge>()
        val entriesMap: Map<Int, GraphEntry> = graphEntries.associateBy { it.entry.id }

        for ((counter, edge) in edges.withIndex()) {
            val graphEdge = createGraphEdge(entriesMap, graph, edge, counter)
            graphEdges.add(graphEdge)
        }
        return graphEdges

    }

    private fun createGraphEdge(
        entriesMap: Map<Int, GraphEntry>,
        graph: Graph<BookEntry, LabeledEdge>,
        edge: LabeledEdge,
        counter: Int
    ): GraphEdge {

        val source = entriesMap.getValue(graph.getEdgeSource(edge).id)
        val dest = entriesMap.getValue(graph.getEdgeTarget(edge).id)
        val startX = source.left + (source.width / 2)
        val startY = source.bottom
        val endX = dest.left + (dest.width / 2)
        val endY = dest.top
        val labelWidth = GraphPaint.textPaint.measureText(edge.label)
        val labelX = (startX + endX - labelWidth) / 2
        val labelY = (startY + endY) / 2 + (OFFSET_LABEL_Y * (counter % 2))

        return GraphEdge(startX, startY, endX, endY, edge.label, labelX, labelY, dest.entry)
    }


}