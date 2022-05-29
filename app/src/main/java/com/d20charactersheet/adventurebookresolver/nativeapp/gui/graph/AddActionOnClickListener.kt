package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.view.View
import androidx.compose.ui.platform.ComposeView
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddActionOnClickListener : KoinComponent, View.OnClickListener {

    private val createActionScreenViewModel: CreateActionScreenViewModel by inject()

    override fun onClick(view: View) {
        val frameLayout = view.parent.parent as View
        val composeView = frameLayout.findViewById<ComposeView>(R.id.create_action_screen)
        createActionScreenViewModel.reset()
        composeView.setContent {
            AdventureBookResolverTheme {
                CreateActionScreen(
                    actionLabel = createActionScreenViewModel.actionLabel,
                    entryId = createActionScreenViewModel.entryId,
                    errorMessage = createActionScreenViewModel.errorMessage,
                    onActionLabelChange = { createActionScreenViewModel.onActionLabelChange(it) },
                    onEntryIdChange = { createActionScreenViewModel.onEntryIdChange(it) },
                    onCancelClick = {
                        composeView.visibility = View.INVISIBLE
                    }
                ) {
                    if (createActionScreenViewModel.onCreateClick()) {
                        composeView.visibility = View.INVISIBLE
                    }
                }

            }
        }
        composeView.visibility = View.VISIBLE
    }

}
