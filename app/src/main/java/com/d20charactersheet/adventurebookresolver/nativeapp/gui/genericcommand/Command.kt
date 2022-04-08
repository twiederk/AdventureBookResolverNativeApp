package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game

@Suppress("unused")
enum class Command(
    val value: String,
    val gameFunction: ((game: Game, argument: String) -> String)
) {

    // use view stack
    Create("Create", { game, argument -> game.createBook(argument) }),
    Path("Path", { game, _ -> game.displayPath() }),
    Run("Run", { game, argument -> game.runTo(argument) }),
    RollDie("Roll die", { game, argument -> game.rollDie(argument) }),

    // use compose
    Search("Search", { game, argument -> SearchCommand().execute(game, argument) }),
    WayPoints("Way points", { game, _ -> DisplayWayPointsCommand().execute(game) }),
    Unvisited("Unvisited", { game, _ -> DisplayActionsToUnvisitedEntriesCommand().execute(game) }),
    Solve("Solve", { game, _ -> SolveCommand().execute(game) });


    fun execute(game: Game, argument: String): String = gameFunction(game, argument)

    override fun toString(): String = value

    companion object {
        fun sortedValues() = values().sortedBy { it.value }
    }

}








