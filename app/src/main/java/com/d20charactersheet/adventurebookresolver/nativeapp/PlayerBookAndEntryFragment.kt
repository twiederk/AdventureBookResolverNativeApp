package com.d20charactersheet.adventurebookresolver.nativeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject

open class PlayerBookAndEntryFragment : Fragment() {

    private val attributesPanel: AttributesPanel by inject()
    private val bookPanel: BookPanel by inject()
    private val entryPanel: EntryPanel by inject()
    private val actionPanel: ActionPanel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_player_book_and_entry, container, false)
        attributesPanel.create(rootView)
        bookPanel.create(rootView)
        entryPanel.create(rootView)
        actionPanel.create(rootView)
        return rootView
    }

}

