package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.Panel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.SearchResult
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GenericCommandPanel : Panel, KoinComponent {

    internal lateinit var commandSpinner: Spinner
    private lateinit var argumentEditText: EditText
    internal lateinit var outputTextView: TextView

    private val genericCommandViewModel: GenericCommandViewModel by inject()

    override fun create(rootView: View) {
        createCommandSpinner(rootView)
        createArgumentEditText(rootView)
        createExecuteButton(rootView)
        createOutputButton(rootView)

        val composeView = rootView.findViewById<ComposeView>(R.id.search_result)
        composeView.setContent {
            MaterialTheme {
                SearchResult(genericCommandViewModel.searchResult)
            }
        }

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
        argumentEditText.addTextChangedListener(ArgumentTextWatcher())
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

    fun getArgument(): String = checkNotNull(genericCommandViewModel.argument.value)

    fun appendOutput(output: String) = outputTextView.append("$output\n")

    fun clearOutput() {
        outputTextView.text = ""
        genericCommandViewModel.onSearchResultChange(emptyList())
    }

}
