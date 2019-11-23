package com.d20charactersheet.adventurebookresolver.nativeapp

import com.d20charactersheet.adventurebookresolver.core.domain.*
import java.io.File

class Game(
    var book: AdventureBook = AdventureBook(),
    private val die: Die = Die(),
    private val bookStore: BookStore = BookStore(),
    private val fileHelper: FileHelper = FileHelper()
) {

    fun createBook(title: String): String {
        book = AdventureBook(title)
        return """Created book "${book.title}""""
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

    fun editBookEntry(entryTitle: String) = book.editBookEntry(entryTitle)

    fun note(note: String) = book.note(note)

    fun restart(): String {
        book.restart()
        return "Restarted book"
    }

    fun addItemToInventory(item: String) = book.addItemToInventory(item)

    fun delete(entryId: Int) = book.delete(entryId)

    fun displayActionsToUnvisitedEntries(): String =
        book.getActionsToUnvisitedEntries().joinToString(
            separator = "\n",
            transform = { action -> "(${action.source.id}) - ${action.source.title}: ${action.label} -> ${action.destination.id}" }
        )

    fun displayPath(): String =
        book.getPath().joinToString(
            separator = "\n",
            transform = { entry -> "(${entry.id}) - ${entry.title}: ${entry.note}" }
        )

    fun removeItemFromInventory(index: Int) = book.removeItemFromInventory(index)

    fun runTo(entryId: String): String {
        book.run(entryId.toInt())
        return "Ran to entry $entryId"
    }

    fun search(criteria: String): String =
        book.search(criteria).joinToString(
            separator = "\n",
            transform = { entry -> "(${entry.id}) - ${entry.title}: ${entry.note}" }
        )


    fun rollDie(dieRoll: String): String = "You rolled ${die.convert(dieRoll)} and scored: ${die.roll(dieRoll)}"

    fun getAction(index: Int): Action = book.getActions().elementAt(index)

    fun getNumberOfActions(): Int = book.getActions().size

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

}