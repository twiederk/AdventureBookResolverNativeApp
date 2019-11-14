package com.d20charactersheet.adventurebookresolver.nativeapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Test


class BookPanelInstrumentedTest {

    @Test
    fun check_initial_layout() {

        // Act
        launchFragmentInContainer<AttributesAndBookFragment>()

        // Assert
        onView(withId(R.id.tries_text_view)).check(matches(withText("Tries:")))
        onView(withId(R.id.tries_value_text_view)).check(matches(withText("1")))
        onView(withId(R.id.entries_text_view)).check(matches(withText("Entries:")))
        onView(withId(R.id.entries_value_text_view)).check(matches(withText("1 / 400 (0%)")))
    }

}
