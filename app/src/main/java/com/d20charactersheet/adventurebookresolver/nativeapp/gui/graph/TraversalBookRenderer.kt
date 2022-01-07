package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import java.lang.Float.max
import java.lang.Float.min
import kotlin.streams.toList

class TraversalBookRenderer(game: Game) : AbstractBookRenderer(game) {

    @Suppress("MemberVisibilityCanBePrivate")
    internal var debug = false

    private companion object {
        const val DEPTH_HEIGHT = 500
        const val ENTRY_HEIGHT = 250
        const val ENTRY_PADDING = 100
    }

    private val graphEntries = mutableListOf<GraphEntry>()
    private var entriesByDepth = mutableMapOf<Int, MutableList<BookEntry>>()

    override fun render(): Pair<List<GraphEntry>, List<GraphEdge>> {
        graphEntries.clear()
        entriesByDepth.clear()

        val rootEntry = game.book.getBookEntry(1)
        entriesByDepth = sortEntriesByDepth(game.book.graph, rootEntry)
        calculateEntry(rootEntry, 0, 0F)

        val graphEdges = calculateGraphEdges(graphEntries)
        return Pair<List<GraphEntry>, List<GraphEdge>>(graphEntries, graphEdges)
    }

    private fun calculateEntry(bookEntry: BookEntry, depth: Int, wall: Float): ChildCords {
        log("calculateEntry(bookEntry = [${bookEntry}], depth = [${depth}], wall = [${wall}])")
        val top = (depth * DEPTH_HEIGHT).toFloat()
        val bottom = top + ENTRY_HEIGHT
        val width = GraphPaint.textPaint.measureText(bookEntry.title)

        if (isLeaf(bookEntry, depth)) {
            val graphEntry = createGraphEntry(bookEntry, wall, top, width, bottom)
            log("* leaf = $graphEntry")
            return ChildCords(graphEntry.left, graphEntry.right)
        }

        // if I have no node to the left of me my children can break the wall
        val childCords = if (siblingsToTheLeft(depth)) {
            traverseChildren(wall, bookEntry, depth)
        } else {
            log("*** breaking the wall with children of $bookEntry *** ")
            traverseChildren(0F, bookEntry, depth)
        }

        val left = centerEntryAboveChildren(wall, width, bookEntry, depth)
        val graphEntry = createGraphEntry(bookEntry, left, top, width, bottom)
        log("* parent = $graphEntry")
        return ChildCords(min(graphEntry.left, childCords.left), max(graphEntry.right, childCords.right))
    }

    private fun siblingsToTheLeft(depth: Int): Boolean {
        val siblings: MutableList<BookEntry> = checkNotNull(entriesByDepth[depth])
        val graphSiblings = mutableListOf<GraphEntry>()
        for (sibling in siblings) {
            val graphEntry = graphEntries.find { graphEntry -> graphEntry.entry.id == sibling.id }
            if (graphEntry != null) {
                graphSiblings.add(graphEntry)
            }
        }
        return graphSiblings.isNotEmpty()
    }

    private fun traverseChildren(
        wall: Float,
        bookEntry: BookEntry,
        depth: Int
    ): ChildCords {
        var childrenLeft = wall
        var childrenRight = wall
        val children = getChildren(bookEntry, depth)
        log("parent: [$bookEntry] children: $children")

        var isFirstChild = true
        for (childEntry in children) {
            log("child: $childEntry")
            val childCords = calculateEntry(
                childEntry,
                depth + 1,
                if (isFirstChild) childrenRight else childrenRight + ENTRY_PADDING
            )
            if (isFirstChild) {
                childrenLeft = childCords.left
            }
            childrenRight = childCords.right
            log("childrenLeft = $childrenLeft")
            log("childrenRight = $childrenRight")
            isFirstChild = false
        }
        return ChildCords(childrenLeft, childrenRight)
    }

    private fun centerEntryAboveChildren(
        wall: Float,
        width: Float,
        bookEntry: BookEntry,
        depth: Int
    ): Float {
        val graphEntryChildren = getGraphEntryChildren(bookEntry, depth)
        val childrenLeft = graphEntryChildren.minOf { it.left }
        val childrenRight = graphEntryChildren.maxOf { it.right }

        val childrenWidth = childrenRight - childrenLeft
        var left = childrenLeft + ((childrenWidth - width) / 2)

        if (left < wall) {
            log("*** never break the wall ***")
            val distanceToWall = wall - left
            left += distanceToWall
            for (entryChild in getDeeperExistingChildren(bookEntry, depth)) {
                val graphChild = checkNotNull(graphEntries.find { graphEntry -> graphEntry.entry == entryChild })
                graphChild.left += distanceToWall
                graphChild.right += distanceToWall
                log("*** updated child: $graphChild")
            }
        }
        return left
    }

    private fun getGraphEntryChildren(bookEntry: BookEntry, depth: Int): List<GraphEntry> {
        val bookEntryChildren = getDeeperExistingChildren(bookEntry, depth)
        return bookEntryChildren.map { checkNotNull(graphEntries.find { graphEntry -> graphEntry.entry == it }) }
    }

    private fun createGraphEntry(
        bookEntry: BookEntry,
        left: Float,
        top: Float,
        width: Float,
        bottom: Float
    ): GraphEntry {
        val graphEntry = GraphEntry(
            entry = bookEntry,
            left = left,
            top = top,
            right = left + width,
            bottom = bottom,
            current = bookEntry.id == game.book.getEntryId()
        )
        graphEntries.add(graphEntry)
        return graphEntry
    }

    private fun getChildren(bookEntry: BookEntry, depth: Int): List<BookEntry> {
        val children = mutableListOf<BookEntry>()
        val allChildren = game.book.graph.outgoingEdgesOf(bookEntry).stream().map { game.book.graph.getEdgeTarget(it) }.toList()
        for (child in allChildren) {
            if (isDeeperChild(child, depth)) {
                if (isNewGraphEntry(child)) {
                    children.add(child)
                }
            }
        }
        return children
    }

    private fun getDeeperExistingChildren(bookEntry: BookEntry, depth: Int): List<BookEntry> {
        val children = mutableListOf<BookEntry>()
        val allChildren = game.book.graph.outgoingEdgesOf(bookEntry).stream().map { game.book.graph.getEdgeTarget(it) }.toList()
        for (child in allChildren) {
            if (isDeeperChild(child, depth)) {
                if (!isNewGraphEntry(child)) {
                    children.add(child)
                }
            }
        }
        return children
    }

    private fun isNewGraphEntry(bookEntry: BookEntry): Boolean = !graphEntries.stream().anyMatch { it.entry == bookEntry }

    private fun isDeeperChild(child: BookEntry, depth: Int): Boolean {
        for (index in ((depth + 1)..entriesByDepth.size)) {
            if (entriesByDepth[index]?.contains(child) == true) {
                return true
            }
        }
        return false
    }

    private fun isLeaf(bookEntry: BookEntry, depth: Int): Boolean = getChildren(bookEntry, depth).isEmpty()

    private fun log(message: String) {
        if (debug) {
            println(message)
        }
    }

}

class ChildCords(val left: Float, val right: Float)
