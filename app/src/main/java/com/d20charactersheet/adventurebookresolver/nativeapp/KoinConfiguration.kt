package com.d20charactersheet.adventurebookresolver.nativeapp

import org.koin.dsl.module

val appModule = module {
    single { Game() }
    single { AttributesPanel(get()) }
    single { BookPanel(get()) }
    single { EntryPanel(get()) }
    single { ActionPanel(get()) }
}