package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.LogFragment
import org.koin.android.ext.android.inject

class InventoryFragment : LogFragment() {

    private val goldPanel: GoldPanel by inject()
    private val provisionsPanel: ProvisionsPanel by inject()
    private val itemPanel: ItemPanel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_inventory, container, false)
        goldPanel.create(rootView)
        provisionsPanel.create(rootView)
        itemPanel.create(rootView)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        goldPanel.update()
        provisionsPanel.update()
        itemPanel.update()
    }

}
