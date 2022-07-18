package com.d20charactersheet.adventurebookresolver.nativeapp

import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.createactionscreen.CreateActionScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.createbookscreen.CreateBookScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.entryscreen.EntryScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.TraversalBookRenderer
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen.FileHelper
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen.GraphScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventoryscreen.InventoryScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.loadscreen.LoadScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen.SolutionScreenViewModel
import org.koin.dsl.module

// tag::koinConfiguration[]
val appModule = module {
    single { FileHelper(get()) }
    single { Game(fileHelper = get()) }
    single { TraversalBookRenderer(get()) }
    single { GraphScreenViewModel(get()) }
    single { SolutionScreenViewModel(get()) }
    single { EntryScreenViewModel(get()) }
    single { CreateBookScreenViewModel(get()) }
    single { CreateActionScreenViewModel(get()) }
    single { InventoryScreenViewModel(get()) }
    single { LoadScreenViewModel(get(), get()) }
}
// end::koinConfiguration[]
