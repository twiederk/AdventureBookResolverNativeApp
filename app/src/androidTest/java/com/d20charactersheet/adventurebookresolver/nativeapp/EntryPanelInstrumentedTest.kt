package com.d20charactersheet.adventurebookresolver.nativeapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class EntryPanelInstrumentedTest : KoinTest {

    private val game: Game by inject()

    @Test
    fun check_initial_layout() {
        // Arrange
        game.note("myNote")

        // Act
        launchFragmentInContainer<PlayerBookAndEntryFragment>()

        // Assert
        onView(withId(R.id.entry_title_edit_text)).check(matches(withText("Untitled")))
        onView(withId(R.id.entry_id_text_view)).check(matches(withText("(1)")))
        onView(withId(R.id.entry_note_edit_text)).check(matches(withText("myNote")))
    }

    @Test
    fun type_entry_title() {

        // Arrange
        launchFragmentInContainer<PlayerBookAndEntryFragment>()

        // Act
        onView(withId(R.id.entry_title_edit_text))
            .perform(ViewActions.typeText("myTitle"))
            .check(matches(withText("myTitle")))

        // Assert
        assertThat(game.book.getEntryTitle()).isEqualTo("myTitle")
    }

    @Test
    fun type_entry_note() {
        // Arrange
        launchFragmentInContainer<PlayerBookAndEntryFragment>()

        // Act
        onView(withId(R.id.entry_note_edit_text))
            .perform(ViewActions.typeText("myNote"))
            .check(matches(withText("myNote")))
    }

}