package com.d20charactersheet.adventurebookresolver.nativeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock

internal class PlayerBookAndEntryFragmentKoinTest : KoinTest {

    private val attributesPanel: AttributesPanel by inject()
    private val bookPanel: BookPanel by inject()
    private val entryPanel: EntryPanel by inject()
    private val actionPanel: ActionPanel by inject()

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }
        declareMock<AttributesPanel>()
        declareMock<BookPanel>()
        declareMock<EntryPanel>()
        declareMock<ActionPanel>()
    }


    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun onCreate() {
        // Arrange
        val container: ViewGroup = mock()
        val rootView: View = mock()

        val inflater = mock<LayoutInflater> {
            on { inflate(R.layout.fragment_player_book_and_entry, container, false) } doReturn rootView
        }
        val savedInstanceState = mock<Bundle>()

        // Act
        PlayerBookAndEntryFragment().onCreateView(inflater, container, savedInstanceState)

        // Assert
        verify(inflater).inflate(R.layout.fragment_player_book_and_entry, container, false)
        verify(attributesPanel).create(any())
        verify(bookPanel).create(any())
        verify(entryPanel).create(any())
        verify(actionPanel).create(any())
    }

}