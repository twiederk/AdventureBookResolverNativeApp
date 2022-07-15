package com.d20charactersheet.adventurebookresolver.nativeapp.gui.solutionscreen

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SolutionBookInfoTest {

    @Test
    fun `should calculate 10 percentage`() {

        // act
        val percentage = calculatePercentage(40, 400)

        // assert
        assertThat(percentage).isEqualTo(10)
    }

    @Test
    fun `should calculate 1 of 3 percentage`() {

        // act
        val percentage = calculatePercentage(400 / 3, 400)

        // assert
        assertThat(percentage).isEqualTo(33)
    }

}