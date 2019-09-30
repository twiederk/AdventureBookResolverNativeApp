package com.d20charactersheet.adventurebookresolver.nativeapp

import com.d20charachtersheet.adventurebookresolver.core.domain.*
import com.nhaarman.mockitokotlin2.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
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
        val result = underTest.addItemToInventory("sword")

        // Assert
        assertThat(result).isEqualTo("""Added "sword" to inventory""")
        verify(book).addItemToInventory("sword")
    }

    @Test
    fun `remove item from inventory`() {
        // Arrange
        val inventory = Inventory()
        inventory.addItem("Leather armor")
        inventory.addItem("Sword")
        whenever(book.inventory).thenReturn(inventory)

        // Act
        val result = underTest.removeItemFromInventory("2")

        // Assert
        assertThat(result).isEqualTo("""Removed "Sword" from inventory""")
        verify(book).removeItemFromInventory(1)
    }


    @Test
    fun `delete action`() {
        // Act
        val result = underTest.delete("2")

        // Assert
        assertThat(result).isEqualTo("Deleted entry 2")
        verify(book).delete(2)
    }

    @Test
    fun `display actions to unvisited entries`() {
        // Arrange
        whenever(book.getActionsToUnvisitedEntries()).thenReturn(
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
        whenever(book.getPath()).thenReturn(
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
        whenever(book.search("start")).thenReturn(
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
    fun changeAttribute() {

        // Act
        underTest.changeAttribute(AttributeName.STRENGTH, 1)

        // Assert
        verify(book).changeAttribute(AttributeName.STRENGTH, 1)
    }

    @Test
    fun rollDie() {

        // Arrange
        whenever(die.roll(anyString())).thenReturn(10)
        whenever(die.convert(anyString())).thenReturn(DieRoll(2, 3))

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
        // Arrange

        // Act
        underTest.addAction("myLabel", 1)

        // Assert
        verify(book).addAction("myLabel", 1)
    }

    @Test
    fun `move to entry`() {
        // Arrange

        // Act
        underTest.move(1)

        // Assert
        verify(book).moveToBookEntry(1)
    }

    @Test
    fun `edit entry title`() {
        // Arrange

        // Act
        val output = underTest.editBookEntry("myEntryTitle")

        // Assert
        assertThat(output).isEqualTo("""Set entry title to "myEntryTitle"""")
        verify(book).editBookEntry("myEntryTitle")
    }

    @Test
    fun note() {
        // Arrange

        // Act
        val output = underTest.note("myNote")

        // Assert
        assertThat(output).isEqualTo("""Set note to "myNote"""")
        verify(book).note("myNote")
    }

    @Test
    fun loadBook() {
        // Arrange
        val oldBook = underTest.book
        val loadedBook = mock<AdventureBook> {
            on { title } doReturn "loaded book title"
        }
        whenever(fileHelper.getDownloadDirectory()).thenReturn(File("downloadDirectory"))
        whenever(bookStore.load(any())).thenReturn(loadedBook)


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
        whenever(fileHelper.getDownloadDirectory()).thenReturn(File("downloadDirectory"))
        whenever(book.title).doReturn("saved book title")
        val destPath = Paths.get("x:${File.separator}destination path")
        whenever(bookStore.save(book, "downloadDirectory${File.separator}saved book title")).thenReturn(destPath)

        // Act
        val output = underTest.saveBook()

        // Assert
        verify(bookStore).save(book, "downloadDirectory${File.separator}saved book title")
        assertThat(output).isEqualTo("""Saved book "saved book title" to "x:${File.separator}destination path"""")
    }

}