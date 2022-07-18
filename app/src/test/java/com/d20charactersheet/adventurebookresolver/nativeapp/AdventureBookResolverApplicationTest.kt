package com.d20charactersheet.adventurebookresolver.nativeapp

import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.createactionscreen.CreateActionScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.createbookscreen.CreateBookScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.entryscreen.EntryScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.TraversalBookRenderer
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen.FileHelper
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen.GraphScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventoryscreen.InventoryScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.loadscreen.LoadScreenViewModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class AdventureBookResolverApplicationTest : KoinTest {

    private val fileHelper: FileHelper by inject()
    private val game: Game by inject()
    private val bookRenderer: TraversalBookRenderer by inject()
    private val graphScreenViewModel: GraphScreenViewModel by inject()
    private val entryScreenViewModel: EntryScreenViewModel by inject()
    private val createBookScreenViewModel: CreateBookScreenViewModel by inject()
    private val createActionScreenViewModel: CreateActionScreenViewModel by inject()
    private val inventoryScreenViewModel: InventoryScreenViewModel by inject()
    private val loadScreenViewModel: LoadScreenViewModel by inject()

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `check application startup with Koin`() {

        // Act
        AdventureBookResolverApplication().onCreate()

        // Assert
        assertThat(fileHelper).isNotNull
        assertThat(game).isNotNull
        assertThat(bookRenderer).isNotNull
        assertThat(graphScreenViewModel).isNotNull
        assertThat(entryScreenViewModel).isNotNull
        assertThat(createBookScreenViewModel).isNotNull
        assertThat(createActionScreenViewModel).isNotNull
        assertThat(inventoryScreenViewModel).isNotNull
        assertThat(loadScreenViewModel).isNotNull
    }
}