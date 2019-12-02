package com.d20charactersheet.adventurebookresolver.nativeapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.core.domain.Inventory
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class ProvisionsPanelInstrumentedTest : KoinTest {

    private val game: Game by inject()

    @Before
    fun before() {
        game.book = AdventureBook(inventory = Inventory(provisions = 4))
    }


    @Test
    fun provisions_increase() {
        // Arrange
        launchFragmentInContainer<InventoryFragment>()

        // Act
        onView(withId(R.id.provisions_plus_button)).perform(ViewActions.click())

        // Assert
        onView(withId(R.id.provisions_value_text_view)).check(matches(withText("5")))
    }

    @Test
    fun provisions_decrease() {
        // Arrange
        launchFragmentInContainer<InventoryFragment>()

        // Act
        onView(withId(R.id.provisions_minus_button)).perform(ViewActions.click())

        // Assert
        onView(withId(R.id.provisions_value_text_view)).check(matches(withText("3")))
    }

    @Test
    fun provisions_eat() {
        // Arrange
        launchFragmentInContainer<InventoryFragment>()

        // Act
        onView(withId(R.id.provisions_eat_button)).perform(ViewActions.click())

        // Assert
        onView(withId(R.id.provisions_value_text_view)).check(matches(withText("3")))
    }

}