package com.d20charactersheet.adventurebookresolver.nativeapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class GenericCommandFragmentIntegrationTest {

    @Test
    fun check_initial_layout() {
        // Act
        launchFragmentInContainer<GenericCommandFragment>()

        // Assert
        onView(withId(R.id.command_spinner)).check(matches(isDisplayed()))
        onView(withId(R.id.argument_edit_text)).check(matches(withText("")))
        onView(withId(R.id.execute_button)).check(matches(withText("Execute")))
        onView(withId(R.id.clear_button)).check(matches(withText("Clear")))
        onView(withId(R.id.output_text_view)).check(matches(withText("")))
    }
}
