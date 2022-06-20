package com.d20charactersheet.adventurebookresolver.nativeapp.domain

import com.d20charactersheet.adventurebookresolver.core.domain.Action
import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.core.domain.AttributeName
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.BookStore
import com.d20charactersheet.adventurebookresolver.core.domain.Die
import com.d20charactersheet.adventurebookresolver.core.domain.DieRoll
import com.d20charactersheet.adventurebookresolver.core.domain.Inventory
import com.d20charactersheet.adventurebookresolver.core.domain.Item
import com.d20charactersheet.adventurebookresolver.core.domain.Solution
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen.FileHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
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
    fun `should return list of actions to unvisited book entries`() {
        // Arrange
        val unvisitedEntries = listOf(
            Action("downstairs", BookEntry(1, "Hallway"), BookEntry(200)),
            Action("left", BookEntry(100, "Tower"), BookEntry(300))
        )

        whenever(book.getActionsToUnvisitedEntries()).doReturn(unvisitedEntries)

        // Act
        val result = underTest.displayActionsToUnvisitedEntries()

        // Assert
        assertThat(result).isSameAs(unvisitedEntries)
        verify(book).getActionsToUnvisitedEntries()
    }

    @Test
    fun `display path`() {
        // Arrange
        val path = listOf(
            BookEntry(id = 1, title = "Hallway", note = "Start of adventure"),
            BookEntry(100, "Tower"),
            BookEntry(200, "Balcony")
        )
        whenever(book.getPath()).doReturn(path)

        // Act
        val result = underTest.displayPath()

        // Assert
        assertThat(result).isEqualTo(path)
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
        val searchResult = listOf(BookEntry(id = 1, title = "Hallway", note = "Start of adventure"))
        whenever(book.search("start")).doReturn(searchResult)

        // Act
        val result = underTest.search("start")

        // Assert
        assertThat(result).isEqualTo(searchResult)
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
        val wayPoints = listOf(
            BookEntry(id = 10, title = "Hallway", note = "my note"), //
            BookEntry(id = 20, title = "Library") //
        )

        whenever(book.getWayPoints()).doReturn(wayPoints)

        // Act
        val output = underTest.displayWayPoints()

        // Assert
        assertThat(output).isSameAs(wayPoints)
    }

    @Test
    fun should_return_empty_string_when_no_solution_is_found() {
        // Arrange
        whenever(book.solve(any())).doReturn(emptyList())

        // Act
        val output = underTest.solve(mock())

        // Assert
        assertThat(output).isEmpty()
    }

    @Test
    fun should_return_all_solutions() {
        // Arrange
        val solution1: Solution = mock()
        whenever(solution1.solutionPath).thenReturn(
            listOf(
                BookEntry(11, wayMark = WayMark.WAY_POINT),
                BookEntry(22),
                BookEntry(33, wayMark = WayMark.WAY_POINT)
            )
        )

        val solution2: Solution = mock()
        whenever(solution2.solutionPath).thenReturn(
            listOf(
                BookEntry(88, wayMark = WayMark.WAY_POINT),
                BookEntry(99, wayMark = WayMark.WAY_POINT)
            )
        )
        val solutionList = listOf(solution1, solution2)

        whenever(book.solve(any())).doReturn(solutionList)

        // Act
        val result: List<Solution> = underTest.solve(mock())

        // Assert
        assertThat(result).isEqualTo(solutionList)
    }

    @Test
    fun isCurrentEntry_entryIsCurrentEntry_returnTrue() {
        // arrange
        whenever(book.getEntryId()).doReturn(1)

        // act
        val result = underTest.isCurrentEntry(BookEntry(1))

        // assert
        assertThat(result).isTrue
    }

    @Test
    fun isCurrentEntry_entryIsNotCurrentEntry_returnFalse() {
        // arrange
        whenever(book.getEntryId()).doReturn(2)

        // act
        val result = underTest.isCurrentEntry(BookEntry(1))

        // assert
        assertThat(result).isFalse
    }

    @Test
    fun isEntryOfNextEntries_entryIsOneOfNextEntries_returnTrue() {
        // arrange
        whenever(book.getNextBookEntries()).doReturn(setOf(BookEntry(2)))

        // act
        val result = underTest.isEntryOfNextEntries(BookEntry(2))

        // assert
        assertThat(result).isTrue
    }

    @Test
    fun isEntryOfNextEntries_entryIsNotOneOfNextEntries_returnFalse() {
        // arrange
        whenever(book.getNextBookEntries()).doReturn(setOf())

        // act
        val result = underTest.isEntryOfNextEntries(BookEntry(2))

        // assert
        assertThat(result).isFalse
    }

    @Test
    fun `should return all actions of the current entry`() {
        // arrange
        val action = Action(
            label = "myLabel",
            source = BookEntry(1),
            destination = BookEntry(2)
        )

        whenever(book.getActions()).doReturn(setOf(action))

        // act
        val result = underTest.getActions()

        // assert
        assertThat(result).containsExactly(action)
    }

    @Test
    fun `should return inventory items`() {
        // arrange
        whenever(book.getItems()).thenReturn(mutableListOf(Item("first item"), Item("second item")))

        // act
        val result = underTest.getItems()

        // assert
        assertThat(result).containsExactly(Item("first item"), Item("second item"))
    }

}