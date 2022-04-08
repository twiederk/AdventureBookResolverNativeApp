package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import com.d20charactersheet.adventurebookresolver.nativeapp.Logger
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommandExecutor {

    fun execute(command: Command, argument: String, game: Game, coroutineScope: CoroutineScope): String {
        if (command == Command.Solve) {
            return executeCoroutine(command, game, argument, coroutineScope)
        }
        return executeMainThread(command, game, argument)
    }

    private fun executeMainThread(
        command: Command,
        game: Game,
        argument: String
    ): String {
        return try {
            command.execute(game, argument)
        } catch (exception: Exception) {
            exception.message ?: "Exception throw with no message"
        }
    }

    private fun executeCoroutine(
        command: Command,
        game: Game,
        argument: String,
        coroutineScope: CoroutineScope
    ): String {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                command.execute(game, argument)
            } catch (exception: Exception) {
                Logger.error(exception.message ?: "Exception throw with no message")
            }
        }
        return ""
    }

}