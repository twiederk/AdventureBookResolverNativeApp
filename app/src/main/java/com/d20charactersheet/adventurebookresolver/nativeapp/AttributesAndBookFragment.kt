package com.d20charactersheet.adventurebookresolver.nativeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.android.ext.android.inject

open class AttributesAndBookFragment : LogFragment() {

    private val attributesPanel: AttributesPanel by inject()
    private val bookPanel: BookPanel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_attributes_and_book, container, false)
        attributesPanel.create(rootView)
        bookPanel.create(rootView)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        attributesPanel.update()
        bookPanel.update()
    }

}

