package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import org.junit.Test
import org.koin.test.KoinTest

class EntryFragmentInstrumentedTest : KoinTest {

    @Test
    fun check_initial_layout() {
        // Act
        launchFragmentInContainer<GraphFragment>(themeResId = R.style.AppTheme)

        // Assert
        onView(withId(R.id.graph_view)).check(matches(isDisplayed()))
        onView(withId(R.id.action_add_floating_action_button)).check(matches(isDisplayed()))
        onView(withId(R.id.save_floating_action_button)).check(matches(isDisplayed()))
    }

}