package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.core.domain.Inventory
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class GoldPanelInstrumentedTest : KoinTest {

    private val game: Game by inject()

    @Before
    fun before() {
        game.book = AdventureBook(inventory = Inventory(gold = 5))
    }


    @Test
    fun gold_increase() {
        // Arrange
        launchFragmentInContainer<InventoryFragment>()

        // Act
        onView(withId(R.id.gold_plus_button)).perform(ViewActions.click())

        // Assert
        onView(withId(R.id.gold_value_text_view)).check(matches(withText("6")))
    }

    @Test
    fun gold_decrease() {
        // Arrange
        launchFragmentInContainer<InventoryFragment>()

        // Act
        onView(withId(R.id.gold_minus_button)).perform(ViewActions.click())

        // Assert
        onView(withId(R.id.gold_value_text_view)).check(matches(withText("4")))
    }


}