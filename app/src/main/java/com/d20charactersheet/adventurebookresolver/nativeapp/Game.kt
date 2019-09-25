package com.d20charactersheet.adventurebookresolver.nativeapp

import com.d20charachtersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charachtersheet.adventurebookresolver.core.domain.AttributeName
import com.d20charachtersheet.adventurebookresolver.core.domain.BookStore
import com.d20charachtersheet.adventurebookresolver.core.domain.Die

class Game(
    var book: AdventureBook = AdventureBook(),
    private val die: Die = Die(),
    private val bookStore: BookStore = BookStore()
) {

    fun createBook(title: String): String {
        book = AdventureBook(title)
        return """Created book "${book.title}""""
    }

//    fun loadBook(filename: String): String {
//        val fileHandle = Gdx.files.external("download/$filename.abr")
//        book = bookStore.import(fileHandle.reader().readLines())
//        return """Loaded book "${book.title}" from ${fileHandle.file().absolutePath}"""
//    }
//
//    fun saveBook(): String {
//        val fileHandle = Gdx.files.external("download/${book.title}.abr")
//        fileHandle.writeString(bookStore.export(book), false)
//        return """Saved book "${book.title}" to ${fileHandle.file().absolutePath}"""
//    }

    fun addAction(actionLabel: String, destinationId: Int) {
        book.addAction(actionLabel, destinationId)
    }

    fun move(entryId: Int) {
        book.moveToBookEntry(entryId)
    }

    fun editBookEntry(entryTitle: String): String {
        book.editBookEntry(entryTitle)
        return """Set entry title to "$entryTitle""""
    }

    fun note(note: String): String {
        book.note(note)
        return """Set note to "$note""""
    }

    fun restart(): String {
        book.restart()
        return "Restarted book"
    }

    fun addItemToInventory(item: String): String {
        book.addItemToInventory(item)
        return """Added "$item" to inventory"""
    }

    fun delete(entryId: String): String {
        book.delete(entryId.toInt())
        return "Deleted entry $entryId"
    }

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

    fun removeItemFromInventory(indexArg: String): String {
        val index = indexArg.toInt() - 1
        val name = book.inventory.items[index].name
        book.removeItemFromInventory(index)
        return """Removed "$name" from inventory"""
    }

    fun runTo(entryId: String): String {
        book.run(entryId.toInt())
        return "Ran to entry $entryId"
    }

    fun search(criteria: String): String =
        book.search(criteria).joinToString(
            separator = "\n",
            transform = { entry -> "(${entry.id}) - ${entry.title}: ${entry.note}" }
        )

    fun changeAttribute(attributeName: AttributeName, value: Int) {
        book.changeAttribute(attributeName, value)
    }

    fun rollDie(dieRoll: String): String {
        return "You rolled ${die.convert(dieRoll)} and scored: ${die.roll(dieRoll)}"
    }

}