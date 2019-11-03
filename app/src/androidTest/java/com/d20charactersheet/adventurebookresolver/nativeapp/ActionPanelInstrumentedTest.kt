package com.d20charactersheet.adventurebookresolver.nativeapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class ActionPanelInstrumentedTest : KoinTest {

    private val game: Game by inject()

    @Before
    fun before() {
        game.book = AdventureBook()
    }

    @Test
    fun check_initial_layout() {
        // Act
        launchFragmentInContainer<EntryFragment>()

        // Assert
        onView(withId(R.id.action_label_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.action_id_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.action_add_button)).check(matches(withText("Add")))
        onView(withId(R.id.action_move_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun add_action_to_entry() {
        // Arrange
        launchFragmentInContainer<AttributesAndBookPanel>()
        launchFragmentInContainer<EntryFragment>()

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

    @Test
    fun move_to_other_entry() {
        // Arrange
        game.book.addAction("to Library", 2)
        launchFragmentInContainer<EntryFragment>()

        // Act
        onView(withId(R.id.move_action_entry_id_button)).perform(ViewActions.click())

        // Assert
        onView(withId(R.id.entry_title_edit_text)).check(matches(withText("Untitled")))
        onView(withId(R.id.entry_id_text_view)).check(matches(withText("(2)")))
    }

    @Test
    fun delete_action_with_swipe() {
        // Arrange
        game.book.addAction("to delete", 2)
        launchFragmentInContainer<EntryFragment>()

        // Act
        onView(withId(R.id.move_action_label_text_view)).perform(ViewActions.swipeLeft())
        onView(withId(R.id.move_action_label_text_view)).perform(ViewActions.swipeLeft())

        // Assert
        onView(withId(R.id.action_move_recycler_view)).check(RecyclerViewItemCountAssertion.withItemCount(0))
        onView(withId(R.id.action_move_recycler_view)).check(RecyclerViewItemCountAssertion.withItemCount(equalTo(0)))
    }
}