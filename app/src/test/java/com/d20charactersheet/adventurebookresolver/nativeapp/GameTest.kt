package com.d20charactersheet.adventurebookresolver.nativeapp

import com.d20charactersheet.adventurebookresolver.core.domain.*
import com.nhaarman.mockitokotlin2.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.io.File
import java.nio.file.Paths

internal class GameTest {

    private lateinit var book: AdventureBook
    private lateinit var die: Die
    private lateinit var bookStore: BookStore
    private lateinit var underTest: Game
    private lateinit var fileHelper: FileHelper

    @Before
    fun before() {
        book = mock()
        die = mock()
        bookStore = mock()
        fileHelper = mock()
        underTest = Game(book, die, bookStore, fileHelper)
    }

    @Test
    fun `restart game`() {
        // Act
        val result = underTest.restart()

        // Assert
        assertThat(result).isEqualTo("Restarted book")
        verify(book).restart()
    }

    @Test
    fun `add item to inventory`() {
        // Act
        underTest.addItemToInventory("sword")

        // Assert
        verify(book).addItemToInventory("sword")
    }

    @Test
    fun `remove item from inventory`() {
        // Arrange
        val inventory = Inventory()
        inventory.addItem("Leather armor")
        inventory.addItem("Sword")
        whenever(book.inventory).doReturn(inventory)

        // Act
        underTest.removeItemFromInventory(1)

        // Assert
        verify(book).removeItemFromInventory(1)
    }


    @Test
    fun `delete action`() {
        // Act
        underTest.delete(2)

        // Assert
        verify(book).delete(2)
    }

    @Test
    fun `display actions to unvisited entries`() {
        // Arrange
        whenever(book.getActionsToUnvisitedEntries()).doReturn(
            listOf(
                Action("downstairs", BookEntry(1, "Hallway"), BookEntry(200)),
                Action("left", BookEntry(100, "Tower"), BookEntry(300))
            )
        )

        // Act
        val result = underTest.displayActionsToUnvisitedEntries()

        // Assert
        assertThat(result).isEqualToIgnoringNewLines(
            """
            (1) - Hallway: downstairs -> 200
            (100) - Tower: left -> 300
        """.trimIndent()
        )
        verify(book).getActionsToUnvisitedEntries()
    }

    @Test
    fun `display path`() {
        // Arrange
        whenever(book.getPath()).doReturn(
            listOf(
                BookEntry(id = 1, title = "Hallway", note = "Start of adventure"),
                BookEntry(100, "Tower"),
                BookEntry(200, "Balcony")
            )
        )

        // Act
        val result = underTest.displayPath()

        // Assert
        assertThat(result).isEqualToIgnoringNewLines(
            """
            (1) - Hallway: Start of adventure
            (100) - Tower: 
            (200) - Balcony: 
                    """.trimIndent()
        )
        verify(book).getPath()
    }

    @Test
    fun `run to book entry`() {
        // Act
        val result = underTest.runTo("100")

        // Assert
        assertThat(result).isEqualTo("Ran to entry 100")
        verify(book).run(100)
    }

    @Test
    fun search() {
        // Arrange
        whenever(book.search("start")).doReturn(
            listOf(
                BookEntry(id = 1, title = "Hallway", note = "Start of adventure")
            )
        )

        // Act
        val result = underTest.search("start")

        // Assert
        assertThat(result).isEqualTo("(1) - Hallway: Start of adventure")
        verify(book).search("start")
    }

    @Test
    fun increaseAttribute() {

        // Act
        underTest.increaseAttribute(AttributeName.STRENGTH, 1)

        // Assert
        verify(book).increaseAttribute(AttributeName.STRENGTH, 1)
    }

    @Test
    fun decreaseAttribute() {

        // Act
        underTest.decreaseAttribute(AttributeName.STRENGTH, 1)

        // Assert
        verify(book).decreaseAttribute(AttributeName.STRENGTH, 1)
    }



    @Test
    fun rollDie() {

        // Arrange
        whenever(die.roll("2d6+3")).doReturn(10)
        whenever(die.convert(any())).doReturn(DieRoll(2, 3))

        // Act
        val result = underTest.rollDie("2d6+3")

        // Assert
        assertThat(result).isEqualTo("You rolled 2d6+3 and scored: 10")
        argumentCaptor<String> {
            verify(die).roll(capture())
            assertThat(firstValue).isEqualTo("2d6+3")
        }

    }

    @Test
    fun `create book`() {
        // Arrange
        val oldBook = underTest.book

        // Act
        underTest.createBook("myTitle")

        // Assert
        assertThat(underTest.book).isNotSameAs(oldBook)
        assertThat(underTest.book.title).isEqualTo("myTitle")
    }

    @Test
    fun `add action`() {
        // Act
        underTest.addAction("myLabel", 1)

        // Assert
        verify(book).addAction("myLabel", 1)
    }

    @Test
    fun `move to entry`() {
        // Act
        underTest.move(1)

        // Assert
        verify(book).moveToBookEntry(1)
    }

    @Test
    fun setEntryTitle() {
        // Act
        underTest.setEntryTitle("myEntryTitle")

        // Assert
        verify(book).setEntryTitle("myEntryTitle")
    }

    @Test
    fun setEntryNote() {
        // Act
        underTest.setEntryNote("myEntryNote")

        // Assert
        verify(book).setEntryNote("myEntryNote")
    }

    @Test
    fun loadBook() {
        // Arrange
        val oldBook = underTest.book
        val loadedBook = mock<AdventureBook> {
            on { title } doReturn "loaded book title"
        }
        whenever(fileHelper.getDownloadDirectory()).doReturn(File("downloadDirectory"))
        whenever(bookStore.load(any())).doReturn(loadedBook)


        // Act
        val output = underTest.loadBook("bookToLoad")

        // Assert
        verify(bookStore).load("downloadDirectory${File.separator}bookToLoad")
        assertThat(output).isEqualTo("""Loaded book "loaded book title" from "downloadDirectory${File.separator}bookToLoad"""")
        assertThat(underTest.book).isNotSameAs(oldBook)
    }

    @Test
    fun saveBook() {
        // Arrange
        whenever(fileHelper.getDownloadDirectory()).doReturn(File("downloadDirectory"))
        whenever(book.title).doReturn("saved book title")
        val destPath = Paths.get("x:${File.separator}destination path")
        whenever(bookStore.save(book, "downloadDirectory${File.separator}saved book title")).doReturn(destPath)

        // Act
        underTest.saveBook()

        // Assert
        verify(bookStore).save(book, "downloadDirectory${File.separator}saved book title")
    }

    @Test
    fun getAction() {
        // Arrange
        val sourceEntry = BookEntry(1)
        val destinationEntry = BookEntry(2)
        whenever(book.getActions()).doReturn(setOf(Action("myAction", sourceEntry, destinationEntry)))

        // Act
        val action = underTest.getAction(0)

        // Assert
        assertThat(action.label).isEqualTo("myAction")
        assertThat(action.source).isEqualTo(sourceEntry)
        assertThat(action.destination).isEqualTo(destinationEntry)
    }

    @Test
    fun getNumberOfActions() {
        // Arrange
        whenever(book.getActions()).doReturn(setOf(Action("myAction", BookEntry(1), BookEntry(2))))

        // Act
        val numberOfActions = underTest.getNumberOfActions()

        // Assert
        assertThat(numberOfActions).isEqualTo(1)
    }

    @Test
    fun getNumberOfItems() {
        // Arrange
        val inventory: Inventory = mock {
            on { items } doReturn mutableListOf(Item("1"), Item("2"), Item("3"), Item("4"), Item("5"))
        }
        whenever(book.inventory).doReturn(inventory)

        // Act
        val numberOfItems: Int = underTest.getNumberOfItems()

        // Assert
        assertThat(numberOfItems).isEqualTo(5)
    }

    @Test
    fun getGold() {
        // Arrange
        whenever(book.getGold()).doReturn(5)

        // Act
        val gold = underTest.getGold()

        // Assert
        assertThat(gold).isEqualTo("5")
    }

    @Test
    fun increaseGold() {
        // Act
        underTest.increaseGold()

        // Assert
        verify(book).editGold(1)
    }

    @Test
    fun decreaseGold() {
        // Act
        underTest.decreaseGold()

        // Assert
        verify(book).editGold(-1)
    }


}