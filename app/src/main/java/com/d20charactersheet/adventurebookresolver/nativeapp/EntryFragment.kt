package com.d20charactersheet.adventurebookresolver.nativeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.android.ext.android.inject

open class EntryFragment : LogFragment() {

    private val entryPanel: EntryPanel by inject()
    private val actionPanel: ActionPanel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_entry, container, false)
        entryPanel.create(rootView)
        actionPanel.create(rootView)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        entryPanel.update()
        actionPanel.update()
    }

}

