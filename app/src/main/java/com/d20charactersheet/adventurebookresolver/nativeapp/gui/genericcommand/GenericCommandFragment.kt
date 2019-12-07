package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.LogFragment
import org.koin.android.ext.android.inject

class GenericCommandFragment : LogFragment() {

    private val genericCommandPanel: GenericCommandPanel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_generic_command, container, false)
        genericCommandPanel.create(rootView)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        genericCommandPanel.update()
    }

}

