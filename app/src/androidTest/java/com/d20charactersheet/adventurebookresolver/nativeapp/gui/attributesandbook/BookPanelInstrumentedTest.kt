package com.d20charactersheet.adventurebookresolver.nativeapp.gui.attributesandbook

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject


class BookPanelInstrumentedTest : KoinTest {

    private val game: Game by inject()

    @Before
    fun before() {
        game.book = AdventureBook()
    }

    @Test
    fun check_initial_layout() {

        // Act
        launchFragmentInContainer<AttributesAndBookFragment>()

        // Assert
        onView(withId(R.id.tries_text_view)).check(matches(withText("Tries:")))
        onView(withId(R.id.tries_value_text_view)).check(matches(withText("1")))
        onView(withId(R.id.entries_text_view)).check(matches(withText("Entries:")))
        onView(withId(R.id.entries_value_text_view)).check(matches(withText("1 / 400 (0%)")))
        onView(withId(R.id.book_note_edit_text)).check(matches(withText("")))
    }

    @Test
    fun type_entry_note() {
        // Arrange
        launchFragmentInContainer<AttributesAndBookFragment>()

        // Act
        onView(withId(R.id.book_note_edit_text))
            .perform(ViewActions.typeText("myBookNote"))
            .check(matches(withText("myBookNote")))

    }


}
