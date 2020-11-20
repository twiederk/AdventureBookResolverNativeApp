package com.d20charactersheet.adventurebookresolver.nativeapp.domain

import com.d20charactersheet.adventurebookresolver.core.domain.*
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand.FileHelper
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
        underTest = Game(
            book,
            die,
            bookStore,
            fileHelper
        )
    }

    @Test
    fun `restart game`() {
        // Act
        underTest.restart()

        // Assert
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

    @Test
    fun getProvisions() {
        // Arrange
        whenever(book.getProvisions()).doReturn(5)

        // Act
        val provisions = underTest.getProvisions()

        // Assert
        assertThat(provisions).isEqualTo("5")
    }

    @Test
    fun increaseProvisions() {
        // Act
        underTest.increaseProvisions()

        // Assert
        verify(book).editProvisions(1)
    }

    @Test
    fun decreaseProvisions() {
        // Act
        underTest.decreaseProvisions()

        // Assert
        verify(book).editProvisions(-1)
    }

    @Test
    fun eatProvision() {
        // Act
        underTest.eatProvision()

        // Assert
        verify(book).eatProvision()
    }

    @Test
    fun getEntryFromNextEntries() {
        // Arrange
        whenever(book.getNextBookEntries()).doReturn(setOf(BookEntry(10)))

        // Act
        val entry = underTest.getEntryFromNextEntries(10)

        // Assert
        assertThat(entry.id).isEqualTo(10)
    }

    @Test
    fun hasProvisions_zeroProvisionsLeft() {
        // Arrange
        whenever(book.getProvisions()).doReturn(0)

        // Act
        val hasProvisions = underTest.hasProvisions()

        // Assert
        assertThat(hasProvisions).isFalse
    }

    @Test
    fun hasProvisions_oneProvisionsLeft() {
        // Arrange
        whenever(book.getProvisions()).doReturn(1)

        // Act
        val hasProvisions = underTest.hasProvisions()

        // Assert
        assertThat(hasProvisions).isTrue
    }

    @Test
    fun markWayMark_asWayPoint() {
        // Arrange
        whenever(book.getEntryId()).doReturn(1)
        whenever(book.getEntryTitle()).doReturn("myTitle")
        whenever(book.getEntryWayMark()).doReturn(WayMark.WAY_POINT)

        // Act
        val output = underTest.setWayMark("NORMAL")

        // Assert
        verify(book).setEntryWayMark(WayMark.NORMAL)
        assertThat(output).isEqualTo("Set (1) - myTitle to WAY_POINT")
    }

    @Test
    fun displayWayPoints() {
        // Arrange
        whenever(book.getWayPoints()).doReturn(
            listOf(
                BookEntry(id = 10, title = "Hallway", note = "my note"), //
                BookEntry(id = 20, title = "Library") //
            )
        )

        // Act
        val output = underTest.displayWayPoints()

        // Assert
        assertThat(output).isEqualToIgnoringNewLines(
            """       
            (10) Hallway: my note
            (20) Library: 
        """.trimIndent()
        )
    }

    @Test
    fun solve() {
        // Arrange
        whenever(book.solve()).doReturn(
            listOf(
                listOf(
                    BookEntry(11, wayMark = WayMark.WAY_POINT),
                    BookEntry(22),
                    BookEntry(33, wayMark = WayMark.WAY_POINT)
                ),
                listOf(BookEntry(88, wayMark = WayMark.WAY_POINT), BookEntry(99, wayMark = WayMark.WAY_POINT))
            )
        )

        // Act
        val output = underTest.solve()

        // Assert
//        - display way points and number of entries:
//        - Order of way points: 1, 3, 5
//        - Total of entries: 15

        assertThat(output).isEqualToIgnoringNewLines(
            """
            11, 33 (entries: 3)
            88, 99 (entries: 2)
            """.trimIndent()
        )
    }

    @Test
    fun touch_destinationEntry_moveToDestinationEntry() {
        // arrange
        val destEntry = BookEntry(1)

        // act
        underTest.touch(destEntry)

        // assert
        verify(book).moveToBookEntry(destEntry.id)
    }

}