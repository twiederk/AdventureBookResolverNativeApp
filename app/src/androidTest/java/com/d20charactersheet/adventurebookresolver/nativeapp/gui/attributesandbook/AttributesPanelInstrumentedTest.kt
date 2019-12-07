package com.d20charactersheet.adventurebookresolver.nativeapp.gui.attributesandbook

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.core.domain.Attribute
import com.d20charactersheet.adventurebookresolver.core.domain.AttributeName
import com.d20charactersheet.adventurebookresolver.core.domain.Attributes
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
class AttributesPanelInstrumentedTest : KoinTest {

    private val game: Game by inject()

    @Before
    fun before() {
        val attributes = Attributes(
            strength = Attribute(AttributeName.STRENGTH, 14, 20),
            dexterity = Attribute(AttributeName.DEXTERITY, 8, 9),
            luck = Attribute(AttributeName.LUCK, 5, 10)
        )
        game.book = AdventureBook(attributes = attributes)
    }

    @Test
    fun check_initial_layout() {

        // Act
        launchFragmentInContainer<AttributesAndBookFragment>()

        // Assert
        onView(withId(R.id.strength_text_view)).check(
            matches(
                withText(
                    R.string.strength
                )
            )
        )
        onView(withId(R.id.strength_value_text_view)).check(matches(withText("14 / 20")))
        onView(withId(R.id.strength_plus_button)).check(
            matches(
                withText(
                    R.string.plus_sign
                )
            )
        )
        onView(withId(R.id.strength_minus_button)).check(
            matches(
                withText(
                    R.string.minus_sign
                )
            )
        )

        onView(withId(R.id.dexterity_text_view)).check(
            matches(
                withText(
                    R.string.dexterity
                )
            )
        )
        onView(withId(R.id.dexterity_value_text_view)).check(matches(withText("8 / 9")))
        onView(withId(R.id.dexterity_plus_button)).check(
            matches(
                withText(
                    R.string.plus_sign
                )
            )
        )
        onView(withId(R.id.dexterity_minus_button)).check(
            matches(
                withText(
                    R.string.minus_sign
                )
            )
        )

        onView(withId(R.id.luck_text_view)).check(
            matches(
                withText(
                    R.string.luck
                )
            )
        )
        onView(withId(R.id.luck_value_text_view)).check(matches(withText("5 / 10")))
        onView(withId(R.id.luck_plus_button)).check(
            matches(
                withText(
                    R.string.plus_sign
                )
            )
        )
        onView(withId(R.id.luck_minus_button)).check(
            matches(
                withText(
                    R.string.minus_sign
                )
            )
        )
    }

    @Test
    fun strength_increase() {
        // Arrange
        launchFragmentInContainer<AttributesAndBookFragment>()

        // Act
        onView(withId(R.id.strength_plus_button)).perform(ViewActions.click())

        // Assert
        onView(withId(R.id.strength_value_text_view)).check(matches(withText("15 / 20")))
    }

    @Test
    fun strength_decrease() {
        // Arrange
        launchFragmentInContainer<AttributesAndBookFragment>()

        // Act
        onView(withId(R.id.strength_minus_button)).perform(ViewActions.click())

        // Assert
        onView(withId(R.id.strength_value_text_view)).check(matches(withText("13 / 20")))
    }

    @Test
    fun dexterity_increase() {
        // Arrange
        launchFragmentInContainer<AttributesAndBookFragment>()

        // Act
        onView(withId(R.id.dexterity_plus_button)).perform(ViewActions.click())

        // Assert
        onView(withId(R.id.dexterity_value_text_view)).check(matches(withText("9 / 9")))
    }

    @Test
    fun dexterity_decrease() {
        // Arrange
        launchFragmentInContainer<AttributesAndBookFragment>()

        // Act
        onView(withId(R.id.dexterity_minus_button)).perform(ViewActions.click())

        // Assert
        onView(withId(R.id.dexterity_value_text_view)).check(matches(withText("7 / 9")))
    }

    @Test
    fun luck_increase() {
        // Arrange
        launchFragmentInContainer<AttributesAndBookFragment>()

        // Act
        onView(withId(R.id.luck_plus_button)).perform(ViewActions.click())

        // Assert
        onView(withId(R.id.luck_value_text_view)).check(matches(withText("6 / 10")))
    }

    @Test
    fun luck_decrease() {
        // Arrange
        launchFragmentInContainer<AttributesAndBookFragment>()

        // Act
        onView(withId(R.id.luck_minus_button)).perform(ViewActions.click())

        // Assert
        onView(withId(R.id.luck_value_text_view)).check(matches(withText("4 / 10")))
    }

}