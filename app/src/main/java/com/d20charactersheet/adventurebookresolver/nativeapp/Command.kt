package com.d20charactersheet.adventurebookresolver.nativeapp

enum class Command(
    val value: String,
    val gameFunction: ((game: Game, argument: String) -> String)
) {

    Create("Create", { game, argument -> game.createBook(argument) }),
    Unvisited("Unvisited", { game, _ -> game.displayActionsToUnvisitedEntries() }),
    Path("Path", { game, _ -> game.displayPath() }),
    Run("Run", { game, argument -> game.runTo(argument) }),
    Search("Search", { game, argument -> game.search(argument) }),
    Load("Load", { game, argument -> game.loadBook(argument) }),
    RollDie("Roll die", { game, argument -> game.rollDie(argument) }),
    Restart("Restart", { game, _ -> game.restart() });

    fun execute(game: Game, argument: String): String = gameFunction(game, argument)
    override fun toString(): String = value

    companion object {
        fun sortedValues() = values().sortedBy { it.value }
    }
}





