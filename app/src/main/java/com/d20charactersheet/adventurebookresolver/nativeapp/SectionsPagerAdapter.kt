package com.d20charactersheet.adventurebookresolver.nativeapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment =
        when (position) {
            1 -> AttributesAndBookPanel()
            2 -> InventoryFragment()
            3 -> GenericCommandFragment()
            else -> EntryFragment()
        }

    override fun getCount(): Int = 4
}