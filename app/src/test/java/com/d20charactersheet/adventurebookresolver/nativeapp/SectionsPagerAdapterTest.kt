package com.d20charactersheet.adventurebookresolver.nativeapp

import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SectionsPagerAdapterTest {

    private val underTest = SectionsPagerAdapter(mock())

    @Test
    fun count_maxNumberOfFragments_4() {
        // Act
        val count = underTest.count

        // Assert
        assertThat(count).isEqualTo(4)
    }

    @Test
    fun getItem_EntryFragment_atPosition0() {
        // Act
        val fragment = underTest.getItem(0)

        // Assert
        assertThat(fragment).isInstanceOf(EntryFragment::class.java)
    }

    @Test
    fun getItem_AttributesAndBookFragment_atPosition1() {
        // Act
        val fragment = underTest.getItem(1)

        // Assert
        assertThat(fragment).isInstanceOf(AttributesAndBookFragment::class.java)
    }

    @Test
    fun getItem_InventoryFragment_atPosition2() {
        // Act
        val fragment = underTest.getItem(2)

        // Assert
        assertThat(fragment).isInstanceOf(InventoryFragment::class.java)
    }

    @Test
    fun getItem_GenericCommandFragment_atPosition3() {
        // Act
        val fragment = underTest.getItem(3)

        // Assert
        assertThat(fragment).isInstanceOf(GenericCommandFragment::class.java)
    }

}