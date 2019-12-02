package com.d20charactersheet.adventurebookresolver.nativeapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class InventoryFragmentInstrumentedTest : KoinTest {

    private val game: Game by inject()

    @Before
    fun before() {
        game.book = AdventureBook()
    }

    @Test
    fun check_initial_layout() {
        // Act
        launchFragmentInContainer<InventoryFragment>()

        // Assert
        onView(withId(R.id.gold_label_text_view)).check(matches(withText("Gold")))
        onView(withId(R.id.gold_value_text_view)).check(matches(withText("0")))
        onView(withId(R.id.gold_plus_button)).check(matches(withText("+")))
        onView(withId(R.id.gold_minus_button)).check(matches(withText("-")))
        onView(withId(R.id.provisions_label_text_view)).check(matches(withText("Provisions")))
        onView(withId(R.id.provisions_value_text_view)).check(matches(withText("10")))
        onView(withId(R.id.provisions_plus_button)).check(matches(withText("+")))
        onView(withId(R.id.provisions_minus_button)).check(matches(withText("-")))
        onView(withId(R.id.provisions_eat_button)).check(matches(withText("Eat")))
        onView(withId(R.id.item_label_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.item_add_button)).check(matches(withText("Add")))
    }

}