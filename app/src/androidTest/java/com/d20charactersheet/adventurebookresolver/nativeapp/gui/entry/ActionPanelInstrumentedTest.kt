package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entry

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.RecyclerViewItemCountAssertion
import org.hamcrest.Matchers.equalTo
import org.junit.Before
import org.junit.Ignore
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
    @Ignore("The style on this component requires your app theme to be Theme.AppCompat (or a descendant)")
    fun check_initial_layout() {
        // Act
        launchFragmentInContainer<EntryFragment>()

        // Assert
        onView(withId(R.id.action_move_recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.action_add_floating_action_button)).check(matches(isDisplayed()))
    }


    @Test
    @Ignore("The style on this component requires your app theme to be Theme.AppCompat (or a descendant)")
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
    @Ignore("The style on this component requires your app theme to be Theme.AppCompat (or a descendant)")
    fun delete_action_with_swipe() {
        // Arrange
        game.book.addAction("to delete", 2)
        launchFragmentInContainer<EntryFragment>()

        // Act
        onView(withId(R.id.move_action_label_text_view)).perform(ViewActions.swipeLeft())
        onView(withId(R.id.move_action_label_text_view)).perform(ViewActions.swipeLeft())

        // Assert
        onView(withId(R.id.action_move_recycler_view)).check(
            RecyclerViewItemCountAssertion.withItemCount(
                0
            )
        )
        onView(withId(R.id.action_move_recycler_view)).check(
            RecyclerViewItemCountAssertion.withItemCount(
                equalTo(0)
            )
        )
    }
}