package com.d20charactersheet.adventurebookresolver.nativeapp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment =
        when (position) {
            1 -> InventoryFragment()
            2 -> GenericCommandFragment()
            else -> MainFragment()
        }

    override fun getCount(): Int = 3
}