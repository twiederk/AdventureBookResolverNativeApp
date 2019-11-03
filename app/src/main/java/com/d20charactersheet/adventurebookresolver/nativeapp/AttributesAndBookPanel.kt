package com.d20charactersheet.adventurebookresolver.nativeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject

open class AttributesAndBookPanel : Fragment() {

    private val attributesPanel: AttributesPanel by inject()
    private val bookPanel: BookPanel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_attributes_and_book, container, false)
        attributesPanel.create(rootView)
        bookPanel.create(rootView)
        return rootView
    }

}

