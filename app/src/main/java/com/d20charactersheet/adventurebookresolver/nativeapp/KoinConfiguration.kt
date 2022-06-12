package com.d20charactersheet.adventurebookresolver.nativeapp

import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.attributesandbook.AttributesPanel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.attributesandbook.BookPanel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.createactionscreen.CreateActionScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.createbookscreen.CreateBookScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.entryscreen.EntryScreenViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand.GenericCommandViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.*
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen.BookViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen.GraphViewModel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory.GoldPanel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory.ItemPanel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory.ProvisionsPanel
import org.koin.dsl.module

// tag::koinConfiguration[]
val appModule = module {
    single { Game() }
    single { AttributesPanel(get()) }
    single { BookPanel(get()) }
    single { GoldPanel(get()) }
    single { ProvisionsPanel(get()) }
    single { ItemPanel() }
    single { TraversalBookRenderer(get()) }
    single { GraphViewModel() }
    single { GenericCommandViewModel() }
    single { EntryScreenViewModel() }
    single { CreateBookScreenViewModel() }
    single { CreateActionScreenViewModel() }
    single { BookViewModel() }
}
// end::koinConfiguration[]
