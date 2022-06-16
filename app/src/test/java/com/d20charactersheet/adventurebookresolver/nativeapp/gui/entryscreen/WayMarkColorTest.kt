package com.d20charactersheet.adventurebookresolver.nativeapp.gui.entryscreen

import androidx.compose.ui.graphics.Color
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class WayMarkColorTest {

    @Test
    fun getColor_normal_gray() {

        // act
        val color = WayMark.NORMAL.color

        // assert
        assertThat(color).isEqualTo(Color.Gray)
    }

    @Test
    fun getColor_wayPoint_green() {

        // act
        val color = WayMark.WAY_POINT.color

        // assert
        assertThat(color).isEqualTo(Color.Green)
    }

    @Test
    fun getColor_deadEnd_gray() {

        // act
        val color = WayMark.DEAD_END.color

        // assert
        assertThat(color).isEqualTo(Color.Red)
    }
}