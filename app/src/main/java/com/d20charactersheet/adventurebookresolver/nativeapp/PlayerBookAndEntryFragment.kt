package com.d20charactersheet.adventurebookresolver.nativeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

open class PlayerBookAndEntryFragment : Fragment() {

    private val attributesPanel: AttributesPanel = AttributesPanel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_player_book_and_entry, container, false)
        attributesPanel.create(rootView)
        return rootView
    }

}

