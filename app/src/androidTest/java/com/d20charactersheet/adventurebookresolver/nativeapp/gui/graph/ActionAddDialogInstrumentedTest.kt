package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class ActionAddDialogInstrumentedTest : KoinTest {

    private val game: Game by inject()

    @Before
    fun before() {
        game.book = AdventureBook()
    }

    @Test
    fun add_action_to_entry() {
        // Arrange
        launchFragmentInContainer<GraphFragment>(themeResId = R.style.AppTheme)

        onView(withId(R.id.action_add_floating_action_button)).perform(ViewActions.click())
        onView(withId(R.id.action_label_edit_text)).perform(ViewActions.typeText("myActionLabel"))
        onView(withId(R.id.action_id_edit_text)).perform(ViewActions.typeText("10"))

        // Act
        onView(withText("OK")).perform(ViewActions.click())

        // Assert
        assertThat(game.book.getActions()).hasSize(1)
        val action = game.book.getActions().first()
        assertThat(action.label).isEqualTo("myActionLabel")
        assertThat(action.source.id).isEqualTo(1)
        assertThat(action.destination.id).isEqualTo(10)
    }

}