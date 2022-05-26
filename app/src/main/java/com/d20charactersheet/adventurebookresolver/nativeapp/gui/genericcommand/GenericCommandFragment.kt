package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.platform.ComposeView
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.LogFragment
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.BookEntryList
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme.AdventureBookResolverTheme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GenericCommandFragment : KoinComponent, LogFragment() {

    private val genericCommandViewModel: GenericCommandViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_generic_command, container, false)
        create(rootView)
        return rootView
    }

    private fun create(rootView: View) {

        val composeView = rootView.findViewById<ComposeView>(R.id.search_result)
        composeView.setContent {
            AdventureBookResolverTheme {
                Column {
                    GenericCommandScreen(
                        argument = genericCommandViewModel.argument,
                        onArgumentChange = { genericCommandViewModel.onArgumentChange(it) },
                        onSearchClick = { genericCommandViewModel.onSearchClick() },
                        onPathClick = { genericCommandViewModel.onPathClick() },
                        onWayPointClick = { genericCommandViewModel.onWayPointClick() },
                        onUnvisitedClick = { genericCommandViewModel.onUnvisitedClick() },
                        onSolveClick = { genericCommandViewModel.onSolveClick() },
                        onRollDieClick = { genericCommandViewModel.onDieRollClick(it) }
                    )
                    BookEntryList(genericCommandViewModel.bookEntryList)
                    ActionList(genericCommandViewModel.actionList)
                    SolutionScreen(
                        genericCommandViewModel.remainingCombinations,
                        genericCommandViewModel.maxCombinations,
                        genericCommandViewModel.numberOfSolutions,
                        genericCommandViewModel.solutionList
                    )
                    OutputText(genericCommandViewModel.outputText)
                }
            }
        }
    }

}

