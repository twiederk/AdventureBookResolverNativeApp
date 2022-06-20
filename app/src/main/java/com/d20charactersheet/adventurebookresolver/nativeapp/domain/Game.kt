package com.d20charactersheet.adventurebookresolver.nativeapp.domain

import com.d20charactersheet.adventurebookresolver.core.domain.Action
import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.core.domain.AttributeName
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.BookSolverListener
import com.d20charactersheet.adventurebookresolver.core.domain.BookStore
import com.d20charactersheet.adventurebookresolver.core.domain.Die
import com.d20charactersheet.adventurebookresolver.core.domain.Item
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen.FileHelper
import java.io.File

class Game(
    var book: AdventureBook = AdventureBook(),
    private val die: Die = Die(),
    private val bookStore: BookStore = BookStore(),
    private val fileHelper: FileHelper = FileHelper()
) {

    fun createBook(title: String) {
        book = AdventureBook(title)
    }

    fun loadBook(filename: String): String {
        val directory = fileHelper.getDownloadDirectory()
        val file = "$directory${File.separator}$filename"
        book = bookStore.load(file)
        return """Loaded book "${book.title}" from "$file""""
    }

    fun saveBook() {
        val directory = fileHelper.getDownloadDirectory()
        val filename = "$directory${File.separator}${book.title}"
        bookStore.save(book, filename)
    }

    fun addAction(actionLabel: String, destinationId: Int) = book.addAction(actionLabel, destinationId)

    fun move(entryId: Int) = book.moveToBookEntry(entryId)

    fun setEntryTitle(entryTitle: String) = book.setEntryTitle(entryTitle)

    fun setEntryNote(entryNote: String) = book.setEntryNote(entryNote)

    fun restart() {
        book.restart()
    }

    fun addItemToInventory(item: String) = book.addItemToInventory(item)

    fun delete(entryId: Int) = book.delete(entryId)

    fun displayActionsToUnvisitedEntries() = book.getActionsToUnvisitedEntries()

    fun displayPath() = book.getPath()

    fun removeItemFromInventory(index: Int) = book.removeItemFromInventory(index)

    fun runTo(entryId: String): String {
        book.run(entryId.toInt())
        return "Ran to entry $entryId"
    }

    fun search(criteria: String): List<BookEntry> = book.search(criteria)

    fun rollDie(dieRoll: String): String = "You rolled ${die.convert(dieRoll)} and scored: ${die.roll(dieRoll)}"

    fun getAction(index: Int): Action = book.getActions().elementAt(index)

    fun getNumberOfActions(): Int = book.getActions().size

    fun getActions(): List<Action> = book.getActions().toList()

    fun getNumberOfItems(): Int = book.inventory.items.size

    fun increaseAttribute(attributeName: AttributeName, value: Int) {
        book.increaseAttribute(attributeName, value)
    }

    fun decreaseAttribute(attributeName: AttributeName, value: Int) {
        book.decreaseAttribute(attributeName, value)
    }

    fun getGold() = book.getGold().toString()

    fun increaseGold() {
        book.editGold(1)
    }

    fun decreaseGold() {
        book.editGold(-1)
    }

    fun getProvisions() = book.getProvisions().toString()

    fun increaseProvisions() {
        book.editProvisions(1)
    }

    fun decreaseProvisions() {
        book.editProvisions(-1)
    }

    fun eatProvision() {
        book.eatProvision()
    }

    fun getEntryFromNextEntries(entryId: Int) = book.getNextBookEntries().first { it.id == entryId }

    fun isEntryOfNextEntries(entry: BookEntry) = book.getNextBookEntries().contains(entry)

    fun hasProvisions() = book.getProvisions() > 0

    fun setWayMark(wayMark: String): String {
        book.setEntryWayMark(WayMark.valueOf(wayMark))
        return "Set (${book.getEntryId()}) - ${book.getEntryTitle()} to ${book.getEntryWayMark()}"
    }

    fun displayWayPoints(): List<BookEntry> = book.getWayPoints()

    fun solve(bookSolverListener: BookSolverListener) = book.solve(bookSolverListener)

    fun isCurrentEntry(bookEntry: BookEntry): Boolean = bookEntry.id == book.getEntryId()

    fun getItems(): List<Item> = book.getItems().toList()

}