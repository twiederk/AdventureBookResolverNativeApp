package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.platform.ComposeView
import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.core.domain.Attribute
import com.d20charactersheet.adventurebookresolver.core.domain.AttributeName
import com.d20charactersheet.adventurebookresolver.core.domain.Attributes
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GenericCommandFragmentKoinTest : KoinTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val underTest = GenericCommandFragment()
    private val game: Game by inject()

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }

        val attributes = Attributes(
            strength = Attribute(AttributeName.STRENGTH, 14, 20),
            dexterity = Attribute(AttributeName.DEXTERITY, 8, 9),
            luck = Attribute(AttributeName.LUCK, 5, 10)
        )
        game.book = AdventureBook(attributes = attributes)
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun onCreateView() {
        // Arrange
        val container = mock<ViewGroup>()
        val composeView = mock<ComposeView>()
        val rootView = mock<View> {
            on { findViewById<ComposeView>(R.id.search_result) } doReturn composeView
        }
        val inflater = mock<LayoutInflater> {
            on { inflate(R.layout.fragment_generic_command, container, false) } doReturn rootView
        }
        val savedInstanceState = mock<Bundle>()

        // Act
        underTest.onCreateView(inflater, container, savedInstanceState)

        // Assert
        verify(inflater).inflate(R.layout.fragment_generic_command, container, false)
    }

}