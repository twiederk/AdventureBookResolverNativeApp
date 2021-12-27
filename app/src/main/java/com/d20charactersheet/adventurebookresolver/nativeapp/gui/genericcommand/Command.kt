package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game

enum class Command(
    val value: String,
    val gameFunction: ((game: Game, argument: String) -> String)
) {

    Create("Create", { game, argument -> game.createBook(argument) }),
    Unvisited("Unvisited", { game, _ -> game.displayActionsToUnvisitedEntries() }),
    Path("Path", { game, _ -> game.displayPath() }),
    Run("Run", { game, argument -> game.runTo(argument) }),
    Search("Search", { game, argument -> game.search(argument) }),
    RollDie("Roll die", { game, argument -> game.rollDie(argument) }),
    WayPoints("Way points", { game, _ -> game.displayWayPoints() }),
    Solve("Solve", { game, _ -> game.solve() }),
    SetWayMark("Set way mark", { game, argument -> game.setWayMark(argument) });

    fun execute(game: Game, argument: String): String = gameFunction(game, argument)
    override fun toString(): String = value

    companion object {
        fun sortedValues() = values().sortedBy { it.value }
    }
}





