package com.d20charactersheet.adventurebookresolver.nativeapp

import com.d20charactersheet.adventurebookresolver.nativeapp.billing.Billing
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.ToolbarPanel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.attributesandbook.AttributesPanel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.attributesandbook.BookPanel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand.GenericCommandPanel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.BookRenderer
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.EntryDialog
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.GraphPanel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph.GraphViewTouchEventHandler
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory.GoldPanel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory.ItemPanel
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory.ProvisionsPanel
import org.koin.dsl.module

// tag::koinConfiguration[]
val appModule = module {
    single { Game() }
    single { ToolbarPanel(get()) }
    single { AttributesPanel(get()) }
    single { BookPanel(get()) }
    single { GenericCommandPanel() }
    single { GoldPanel(get()) }
    single { ProvisionsPanel(get()) }
    single { ItemPanel() }
    single { GraphPanel(get()) }
    single { BookRenderer(get()) }
    single { Billing() }
    single { EntryDialog() }
    single { GraphViewTouchEventHandler() }
}
// end::koinConfiguration[]
