package com.d20charactersheet.adventurebookresolver.nativeapp.gui.createbookscreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen.BookViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventoryscreen.InventoryScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen.SolutionScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.junit.Rule
import org.junit.Test

class CreateBookScreenComposeTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_create_book_screen() {
        // arrange
        val game = Game()
        val createBookScreenViewModel = CreateBookScreenViewModel(game)
        createBookScreenViewModel.onTitleChange("myTitle")

        // act
        composeTestRule.setContent {
            AdventureBookResolverTheme {
                CreateBookScreen(
                    createBookScreenViewModel = createBookScreenViewModel,
                    bookViewModel = BookViewModel(game),
                    solutionScreenViewModel = SolutionScreenViewModel(game),
                    inventoryScreenViewModel = InventoryScreenViewModel(game),
                    navController = rememberNavController()
                )
            }
        }

        // assert
        composeTestRule.onNodeWithText("Create Book Screen").assertIsDisplayed()
        composeTestRule.onNodeWithText("Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("myTitle").assertIsDisplayed()
        composeTestRule.onNodeWithText("Create").assertIsDisplayed()
    }

}