package com.d20charactersheet.adventurebookresolver.nativeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.android.ext.android.inject

class InventoryFragment : LogFragment() {

    private val inventoryPanel: InventoryPanel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_inventory, container, false)
        inventoryPanel.create(rootView)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        inventoryPanel.update()
    }

}
