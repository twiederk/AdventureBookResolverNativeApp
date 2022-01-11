package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchCommand : KoinComponent {

    private val genericCommandViewModel: GenericCommandViewModel by inject()

    fun execute(game: Game, argument: String): String {
        val searchResult = game.search(argument)
        genericCommandViewModel.onBookEntryListChange(searchResult)
        return ""
    }

}
