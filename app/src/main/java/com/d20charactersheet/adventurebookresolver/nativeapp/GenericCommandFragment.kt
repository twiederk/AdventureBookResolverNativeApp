package com.d20charactersheet.adventurebookresolver.nativeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class GenericCommandFragment : Fragment() {

    internal lateinit var commandSpinner: Spinner
    internal lateinit var argumentEditText: EditText
    internal lateinit var outputTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_generic_command, container, false)
        createCommandSpinner(rootView)
        createArgumentEditText(rootView)
        createExecuteButton(rootView)
        createOutputButton(rootView)
        createOutputTextView(rootView)
        return rootView
    }

    private fun createCommandSpinner(rootView: View) {
        commandSpinner = rootView.findViewById(R.id.command_spinner)

        ArrayAdapter(
            context,
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
        executeButton.setOnClickListener(ExecuteOnClickListener(this, MainActivity.game))
    }


    private fun createOutputButton(rootView: View) {
        val clearButton: Button = rootView.findViewById(R.id.clear_button)
        clearButton.setOnClickListener(ClearOnClickListener(this))
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


class ExecuteOnClickListener(private val genericCommandFragment: GenericCommandFragment, private val game: Game) :
    View.OnClickListener {

    override fun onClick(v: View?) {
        val output = executeCommand()
        genericCommandFragment.appendOutput(output)
        (genericCommandFragment.activity as MainActivity).update()
    }

    private fun executeCommand(): String {
        val command = genericCommandFragment.getSelectedCommand()
        val argument = genericCommandFragment.getArgument()
        return command.execute(game, argument)
    }
}

class ClearOnClickListener(private val genericCommandFragment: GenericCommandFragment) : View.OnClickListener {

    override fun onClick(v: View?) {
        genericCommandFragment.clearOutput()
    }

}
