package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.view.InputDevice
import android.view.MotionEvent
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.GeneralClickAction
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.Tap
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.RecyclerViewItemCountAssertion
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class EntryDialogInstrumentedTest : KoinTest {

    private val game: Game by inject()

    @Before
    fun before() {
        game.book = AdventureBook()
    }

    @Test
    fun click_on_current_entry_opens_entry_dialog() {
        // arrange
        game.book.setEntryNote("myNote")
        game.book.setEntryTitle("myTitle")
        launchFragmentInContainer<GraphFragment>(themeResId = R.style.AppTheme)

        // act
        onView(withId(R.id.graph_view)).perform(clickCenter())

        // assert
        onView(withId(R.id.entry_title_edit_text)).check(matches(withText("myTitle")))
        onView(withId(R.id.entry_id_text_view)).check(matches(withText("(1)")))
        onView(withId(R.id.entry_note_edit_text)).check(matches(withText("myNote")))
    }

    @Test
    fun modify_title_and_note_of_current_entry() {
        // arrange
        game.book.setEntryTitle("")
        launchFragmentInContainer<GraphFragment>(themeResId = R.style.AppTheme)
        onView(withId(R.id.graph_view)).perform(clickCenter())
        onView(withId(R.id.entry_title_edit_text)).perform(ViewActions.typeText("myTitle"))
        onView(withId(R.id.entry_note_edit_text)).perform(ViewActions.typeText("myNote"))

        // act
        onView(withText("OK")).perform(ViewActions.click())


        // assert
        assertThat(game.book.getEntryTitle()).isEqualTo("myTitle")
        assertThat(game.book.getEntryNote()).isEqualTo("myNote")
    }

    @Test
    fun delete_action_with_swipe() {
        // Arrange
        game.book.addAction("to delete", 2)
        launchFragmentInContainer<GraphFragment>(themeResId = R.style.AppTheme)
        onView(withId(R.id.graph_view)).perform(clickCenter())

        // Act
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

    companion object {
        fun clickCenter(): ViewAction {
            return GeneralClickAction(
                Tap.SINGLE,
                { view ->
                    val screenPos = IntArray(2)
                    view.getLocationOnScreen(screenPos)
                    val x = view.width / 2
                    val y = view.height / 2

                    val screenX = (screenPos[0] + x).toFloat()
                    val screenY = (screenPos[1] + y).toFloat()

                    floatArrayOf(screenX, screenY)
                },
                Press.FINGER,
                InputDevice.SOURCE_MOUSE,
                MotionEvent.BUTTON_PRIMARY
            )
        }
    }
}