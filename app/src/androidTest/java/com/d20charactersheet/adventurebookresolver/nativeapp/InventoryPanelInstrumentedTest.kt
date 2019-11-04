package com.d20charactersheet.adventurebookresolver.nativeapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class InventoryPanelInstrumentedTest : KoinTest {

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
        onView(withId(R.id.item_label_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.item_add_button)).check(matches(withText("Add")))
    }

    @Test
    fun add_item_to_inventory() {
        // Arrange
        launchFragmentInContainer<InventoryFragment>()
        onView(withId(R.id.item_label_edit_text)).perform(ViewActions.typeText("myItem"))

        // Act
        onView(withId(R.id.item_add_button)).perform(ViewActions.click())

        // Assert
        assertThat(game.book.inventory.items).hasSize(1)
        val item = game.book.inventory.items[0]
        assertThat(item.name).isEqualTo("myItem")

        onView(withId(R.id.item_label_edit_text)).check(matches(withText("")))
        onView(withId(R.id.item_recycler_view))
            .check(matches(atPosition(0, hasDescendant(withText("myItem")))))
    }

    @Test
    fun delete_item_with_swipe() {
        // Arrange
        game.addItemToInventory("item to delete")
        launchFragmentInContainer<InventoryFragment>()

        // Act
        onView(withId(R.id.item_text_view)).perform(ViewActions.swipeLeft())

        // Assert
        onView(withId(R.id.item_recycler_view)).check(RecyclerViewItemCountAssertion.withItemCount(0))
        onView(withId(R.id.item_recycler_view))
            .check(RecyclerViewItemCountAssertion.withItemCount(Matchers.equalTo(0)))
    }


}