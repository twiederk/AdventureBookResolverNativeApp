package com.d20charactersheet.adventurebookresolver.nativeapp

import org.koin.dsl.module

// tag::koinConfiguration[]
val appModule = module {
    single { Game() }
    single { ToolbarPanel(get()) }
    single { AttributesPanel(get()) }
    single { BookPanel(get()) }
    single { EntryPanel(get()) }
    single { ActionPanel() }
    single { GenericCommandPanel() }
    single { InventoryPanel() }
}
// end::koinConfiguration[]
