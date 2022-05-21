package com.d20charactersheet.adventurebookresolver.nativeapp

import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.ToolbarPanel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.attributesandbook.AttributesPanel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.attributesandbook.BookPanel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.CreateBookScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.EntryScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.GraphPanel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.GraphViewTouchEventHandler
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.TraversalBookRenderer
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory.GoldPanel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory.ItemPanel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory.ProvisionsPanel
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class AdventureBookResolverApplicationTest : KoinTest {

    private val game: Game by inject()
    private val toolbarPanel: ToolbarPanel by inject()
    private val attributesPanel: AttributesPanel by inject()
    private val bookPanel: BookPanel by inject()
    private val goldPanel: GoldPanel by inject()
    private val provisionsPanel: ProvisionsPanel by inject()
    private val itemPanel: ItemPanel by inject()
    private val graphPanel: GraphPanel by inject()
    private val bookRenderer: TraversalBookRenderer by inject()
    private val graphViewTouchEventHandler: GraphViewTouchEventHandler by inject()
    private val entryScreenViewModel: EntryScreenViewModel by inject()
    private val createBookScreenViewModel: CreateBookScreenViewModel by inject()

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `check application startup with Koin`() {

        // Act
        AdventureBookResolverApplication().onCreate()

        // Assert
        assertThat(game).isNotNull
        assertThat(toolbarPanel).isNotNull
        assertThat(attributesPanel).isNotNull
        assertThat(bookPanel).isNotNull
        assertThat(goldPanel).isNotNull
        assertThat(provisionsPanel).isNotNull
        assertThat(itemPanel).isNotNull
        assertThat(graphPanel).isNotNull
        assertThat(bookRenderer).isNotNull
        assertThat(graphViewTouchEventHandler).isNotNull
        assertThat(entryScreenViewModel).isNotNull
        assertThat(createBookScreenViewModel).isNotNull
    }
}