package com.d20charactersheet.adventurebookresolver.nativeapp

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
    private val entryPanel: EntryPanel by inject()
    private val actionPanel: ActionPanel by inject()
    private val genericCommandPanel: ActionPanel by inject()
    private val goldPanel: GoldPanel by inject()
    private val provisionsPanel: ProvisionsPanel by inject()
    private val itemPanel: ItemPanel by inject()

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
        assertThat(entryPanel).isNotNull
        assertThat(actionPanel).isNotNull
        assertThat(genericCommandPanel).isNotNull
        assertThat(goldPanel).isNotNull
        assertThat(provisionsPanel).isNotNull
        assertThat(itemPanel).isNotNull
    }
}