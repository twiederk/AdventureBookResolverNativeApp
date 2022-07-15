package com.d20charactersheet.adventurebookresolver.nativeapp

import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.createactionscreen.CreateActionScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.createbookscreen.CreateBookScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.entryscreen.EntryScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.TraversalBookRenderer
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen.BookViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen.GraphViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventoryscreen.InventoryScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen.SolutionScreenViewModel
import org.koin.dsl.module

// tag::koinConfiguration[]
val appModule = module {
    single { Game() }
    single { TraversalBookRenderer(get()) }
    single { GraphViewModel() }
    single { SolutionScreenViewModel(get()) }
    single { EntryScreenViewModel(get()) }
    single { CreateBookScreenViewModel(get()) }
    single { CreateActionScreenViewModel(get()) }
    single { BookViewModel(get()) }
    single { InventoryScreenViewModel(get()) }
}
// end::koinConfiguration[]
