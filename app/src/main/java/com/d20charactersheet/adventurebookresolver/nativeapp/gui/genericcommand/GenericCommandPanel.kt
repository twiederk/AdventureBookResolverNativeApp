package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import android.view.View
import android.widget.*
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.Panel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.ToolbarPanel
import org.koin.core.KoinComponent
import org.koin.core.inject

class GenericCommandPanel : Panel {

    internal lateinit var commandSpinner: Spinner
    internal lateinit var argumentEditText: EditText
    internal lateinit var outputTextView: TextView

    override fun create(rootView: View) {
        createCommandSpinner(rootView)
        createArgumentEditText(rootView)
        createExecuteButton(rootView)
        createOutputButton(rootView)
        createOutputTextView(rootView)
    }

    override fun update() {}

    private fun createCommandSpinner(rootView: View) {
        commandSpinner = rootView.findViewById(R.id.command_spinner)

        ArrayAdapter(
            rootView.context,
            android.R.layout.simple_spinner_item,
            Command.sortedValues().toTypedArray()
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            commandSpinner.adapter = adapter
        }

    }

    private fun createArgumentEditText(rootView: View) {
        argumentEditText = rootView.findViewById(R.id.argument_edit_text)
    }

    private fun createExecuteButton(rootView: View) {
        val executeButton: Button = rootView.findViewById(R.id.execute_button)
        executeButton.setOnClickListener(ExecuteOnClickListener())
    }


    private fun createOutputButton(rootView: View) {
        val clearButton: Button = rootView.findViewById(R.id.clear_button)
        clearButton.setOnClickListener(ClearOnClickListener())
    }

    private fun createOutputTextView(rootView: View) {
        outputTextView = rootView.findViewById(R.id.output_text_view)
    }

    fun getSelectedCommand(): Command = commandSpinner.selectedItem as Command

    fun getArgument(): String = argumentEditText.text.toString()

    fun appendOutput(output: String) = outputTextView.append("$output\n")

    fun clearOutput() {
        outputTextView.text = ""
    }

}

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
        return try {
            command.execute(game, argument)
        } catch (exception: Exception) {
            exception.message ?: "Exception throw with no message"
        }
    }
}

class ClearOnClickListener : View.OnClickListener, KoinComponent {

    private val genericCommandPanel: GenericCommandPanel by inject()

    override fun onClick(v: View?) {
        genericCommandPanel.clearOutput()
    }

}
