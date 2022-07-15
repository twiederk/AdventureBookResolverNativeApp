package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class GraphScreenComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_info_of_book_entry() {
        // arrange
        val game = Game()
        val bookViewModel = BookViewModel(game)
        bookViewModel.onTitleChange("myTitle")

        val graphViewModel = GraphViewModel()
        graphViewModel.onScaleChange(1.0F)

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                GraphScreen(
                    bookViewModel = bookViewModel,
                    graphViewModel = graphViewModel,
                    onLoadClick = { },
                    onRestartClick = { },
                    onRenderClick = { },
                    navController = rememberNavController()
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("myTitle").assertIsDisplayed()
    }

}