package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import android.view.View
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.ToolbarPanel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ExecuteOnClickListener : View.OnClickListener, KoinComponent {

    private val game: Game by inject()
    private val genericCommandPanel: GenericCommandPanel by inject()
    private val toolbarPanel: ToolbarPanel by inject()

    override fun onClick(v: View?) {
        val output = executeCommand()
        genericCommandPanel.appendOutput(output)
        toolbarPanel.update()
    }

    private fun executeCommand(): String {
        val command = genericCommandPanel.getSelectedCommand()
        val argument = genericCommandPanel.getArgument()
        genericCommandPanel.clearOutput()
        return try {
            command.execute(game, argument)
        } catch (exception: Exception) {
            exception.message ?: "Exception throw with no message"
        }
    }
}
