package com.d20charactersheet.adventurebookresolver.nativeapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class ActionPanelInstrumentedTest : KoinTest {

    private val game: Game by inject()

    @Test
    fun check_initial_layout() {
        // Arrange
        game.note("myNote")

        // Act
        launchFragmentInContainer<PlayerBookAndEntryFragment>()

        // Assert
        onView(withId(R.id.action_label_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.action_id_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.action_add_button)).check(matches(withText("Add")))
        onView(withId(R.id.action_move_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun add_action_to_entry() {
        // Arrange
        launchFragmentInContainer<PlayerBookAndEntryFragment>()

        onView(withId(R.id.action_label_edit_text)).perform(ViewActions.typeText("myActionLabel"))
        onView(withId(R.id.action_id_edit_text)).perform(ViewActions.typeText("10"))

        // Act
        onView(withId(R.id.action_add_button))
            .perform(ViewActions.click())

        // Assert
        assertThat(game.book.getActions()).hasSize(1)
        val action = game.book.getActions().first()
        assertThat(action.label).isEqualTo("myActionLabel")
        assertThat(action.source.id).isEqualTo(1)
        assertThat(action.destination.id).isEqualTo(10)

        onView(withId(R.id.action_label_edit_text)).check(matches(withText("")))
        onView(withId(R.id.action_id_edit_text)).check(matches(withText("")))
        onView(withId(R.id.action_move_recycler_view))
            .check(matches(atPosition(0, hasDescendant(withText("myActionLabel")))))

    }
}