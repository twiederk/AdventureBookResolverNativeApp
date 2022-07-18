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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Game(
    var book: AdventureBook = AdventureBook(),
    private val die: Die = Die(),
    private val bookStore: BookStore = BookStore(),
    private val fileHelper: FileHelper = FileHelper(null)
) {

    fun createBook(title: String) {
        book = AdventureBook(title)
        saveBook()
    }

    fun loadBook(filename: String): String {
        val directory = fileHelper.getDownloadDirectory()
        val file = "$directory${File.separator}$filename"
        book = bookStore.load(file)
        return """Loaded book "${book.title}" from "$file""""
    }

    fun saveBook() {
        val bookData = bookStore.export(book)
        fileHelper.saveInternal(
            bookTitle = book.title,
            fileContent = bookData
        )
    }

    fun exportBook(date: LocalDateTime = LocalDateTime.now()) {
        val fileName = getFileName(book.title, date)
        val bookData = bookStore.export(book)
        fileHelper.saveExternal(
            bookTitle = fileName,
            fileContent = bookData
        )
    }

    private fun getFileName(bookTitle: String, date: LocalDateTime): String {
        val formatter = checkNotNull(DateTimeFormatter.ofPattern("yyyyMMdd_kkmm"))
        return "${bookTitle}_${formatter.format(date)}"
    }

    fun addAction(actionLabel: String, destinationId: Int) {
        book.addAction(actionLabel, destinationId)
        saveBook()
    }

    fun move(entryId: Int) {
        book.moveToBookEntry(entryId)
        saveBook()
    }

    fun setEntryTitle(entryTitle: String) {
        book.setEntryTitle(entryTitle)
        saveBook()
    }

    fun setEntryNote(entryNote: String) {
        book.setEntryNote(entryNote)
        saveBook()
    }

    fun restart() {
        book.restart()
        saveBook()
    }

    fun addItemToInventory(item: String) {
        book.addItemToInventory(item)
        saveBook()
    }

    fun delete(entryId: Int) {
        book.delete(entryId)
        saveBook()
    }

    fun displayActionsToUnvisitedEntries() = book.getActionsToUnvisitedEntries()

    fun displayPath() = book.getPath()

    fun removeItemFromInventory(index: Int) {
        book.removeItemFromInventory(index)
        saveBook()
    }

    fun runTo(entryId: Int) {
        book.run(entryId)
        saveBook()
    }

    fun search(criteria: String): List<BookEntry> = book.search(criteria)

    fun rollDie(dieRoll: String): String = "You rolled ${die.convert(dieRoll)} and scored: ${die.roll(dieRoll)}"

    fun getAction(index: Int): Action = book.getActions().elementAt(index)

    fun getNumberOfActions(): Int = book.getActions().size

    fun getActions(bookEntry: BookEntry): List<Action> = book.getActions(bookEntry).toList()

    fun getNumberOfItems(): Int = book.inventory.items.size

    fun increaseAttribute(attributeName: AttributeName, value: Int) {
        book.increaseAttribute(attributeName, value)
        saveBook()
    }

    fun decreaseAttribute(attributeName: AttributeName, value: Int) {
        book.decreaseAttribute(attributeName, value)
        saveBook()
    }

    fun getGold() = book.getGold().toString()

    fun increaseGold() {
        book.editGold(1)
        saveBook()
    }

    fun decreaseGold() {
        book.editGold(-1)
        saveBook()
    }

    fun getProvisions() = book.getProvisions().toString()

    fun increaseProvisions() {
        book.editProvisions(1)
        saveBook()
    }

    fun decreaseProvisions() {
        book.editProvisions(-1)
        saveBook()
    }

    fun eatProvision() {
        book.eatProvision()
        saveBook()
    }

    fun getEntryFromNextEntries(entryId: Int) = book.getNextBookEntries().first { it.id == entryId }

    fun isEntryOfNextEntries(entry: BookEntry) = book.getNextBookEntries().contains(entry)

    fun hasProvisions() = book.getProvisions() > 0

    fun setWayMark(wayMark: String): String {
        book.setEntryWayMark(WayMark.valueOf(wayMark))
        saveBook()
        return "Set (${book.getEntryId()}) - ${book.getEntryTitle()} to ${book.getEntryWayMark()}"
    }

    fun displayWayPoints(): List<BookEntry> = book.getWayPoints()

    fun solve(bookSolverListener: BookSolverListener) = book.solve(bookSolverListener)

    fun isCurrentEntry(bookEntry: BookEntry): Boolean = bookEntry.id == book.getEntryId()

    fun getItems(): List<Item> = book.getItems().toList()

    fun getNumberOfBookEntries(): Int = book.getAllBookEntries().size

    fun getTotalNumberOfBookEntries(): Int = book.totalNumberOfBookEntries

}