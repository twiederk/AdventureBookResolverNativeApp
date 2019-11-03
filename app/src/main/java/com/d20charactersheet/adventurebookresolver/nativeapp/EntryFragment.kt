package com.d20charactersheet.adventurebookresolver.nativeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject

open class EntryFragment : Fragment() {

    private val entryPanel: EntryPanel by inject()
    private val actionPanel: ActionPanel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_entry, container, false)
        entryPanel.create(rootView)
        actionPanel.create(rootView)
        return rootView
    }

}

