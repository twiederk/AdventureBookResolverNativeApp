package com.d20charactersheet.adventurebookresolver.nativeapp.gui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.attributesandbook.AttributesAndBookFragment
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.entry.EntryFragment
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand.GenericCommandFragment
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory.InventoryFragment

class SectionsPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragments = arrayOf(
        EntryFragment(),
        AttributesAndBookFragment(),
        InventoryFragment(),
        GenericCommandFragment()
    )

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size
}