package com.d20charactersheet.adventurebookresolver.nativeapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

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