package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entry

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class EntryPanelInstrumentedTest : KoinTest {

    private val game: Game by inject()

    @Test
    fun check_initial_layout() {
        // Arrange
        game.setEntryNote("myNote")

        // Act
        launchFragmentInContainer<EntryFragment>()

        // Assert
        onView(withId(R.id.entry_title_edit_text)).check(matches(withText("Untitled")))
        onView(withId(R.id.entry_id_text_view)).check(matches(withText("(1)")))
        onView(withId(R.id.entry_note_edit_text)).check(matches(withText("myNote")))
    }

    @Test
    fun type_entry_title() {

        // Arrange
        launchFragmentInContainer<EntryFragment>()

        // Act
        onView(withId(R.id.entry_title_edit_text))
            .perform(ViewActions.typeText("myTitle\t"))
            .check(matches(withText("myTitle")))

        // Assert
        assertThat(game.book.getEntryTitle()).isEqualTo("myTitle")
    }

    @Test
    fun type_entry_note() {
        // Arrange
        launchFragmentInContainer<EntryFragment>()

        // Act
        onView(withId(R.id.entry_note_edit_text))
            .perform(ViewActions.typeText("myEntryNote"))
            .check(matches(withText("myEntryNote")))

    }

}