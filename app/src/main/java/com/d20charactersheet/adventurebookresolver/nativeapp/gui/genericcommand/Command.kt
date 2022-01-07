package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game

@Suppress("unused")
enum class Command(
    val value: String,
    val gameFunction: ((game: Game, argument: String) -> String)
) {

    Create("Create", { game, argument -> game.createBook(argument) }),
    Unvisited("Unvisited", { game, _ -> game.displayActionsToUnvisitedEntries() }),
    Path("Path", { game, _ -> game.displayPath() }),
    Run("Run", { game, argument -> game.runTo(argument) }),
    Search("Search", { game, argument -> SearchCommand().execute(game, argument) }),
    RollDie("Roll die", { game, argument -> game.rollDie(argument) }),
    WayPoints("Way points", { game, _ -> DisplayWayPointsCommand().execute(game) }),
    Solve("Solve", { game, _ -> game.solve() });

    fun execute(game: Game, argument: String): String = gameFunction(game, argument)

    override fun toString(): String = value

    companion object {
        fun sortedValues() = values().sortedBy { it.value }
    }

}








