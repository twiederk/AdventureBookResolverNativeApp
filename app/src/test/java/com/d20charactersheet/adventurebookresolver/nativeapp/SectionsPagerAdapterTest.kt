package com.d20charactersheet.adventurebookresolver.nativeapp

import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SectionsPagerAdapterTest {

    private val underTest = SectionsPagerAdapter(mock())

    @Test
    fun count_maxNumberOfFragments_3() {
        // Act
        val count = underTest.count

        // Assert
        assertThat(count).isEqualTo(3)
    }


    @Test
    fun getItem_PlayerBookAndEntryFragment_atPosition0() {
        // Act
        val fragment = underTest.getItem(0)

        // Assert
        assertThat(fragment).isInstanceOf(PlayerBookAndEntryFragment::class.java)
    }


    @Test
    fun getItem_InventoryFragment_atPosition1() {
        // Act
        val fragment = underTest.getItem(1)

        // Assert
        assertThat(fragment).isInstanceOf(InventoryFragment::class.java)
    }

    @Test
    fun getItem_GenericCommandFragment_atPosition2() {
        // Act
        val fragment = underTest.getItem(2)

        // Assert
        assertThat(fragment).isInstanceOf(GenericCommandFragment::class.java)
    }

}