package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DisplayActionsToUnvisitedEntriesCommand : KoinComponent {

    private val genericCommandViewModel: GenericCommandViewModel by inject()

    fun execute(game: Game): String {
        val actions = game.displayActionsToUnvisitedEntries()
        genericCommandViewModel.onActionListChange(actions)
        return ""
    }

}