package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game

enum class Command(
    val value: String,
    val gameFunction: ((game: Game, argument: String) -> String)
) {
    Create("Create", { game, argument -> game.createBook(argument) }),
    Run("Run", { game, argument -> game.runTo(argument) }),
    RollDie("Roll die", { game, argument -> game.rollDie(argument) }),
    Search("Search", { game, argument -> SearchCommand().execute(game, argument) });

    fun execute(game: Game, argument: String): String = gameFunction(game, argument)

    override fun toString(): String = value

}








