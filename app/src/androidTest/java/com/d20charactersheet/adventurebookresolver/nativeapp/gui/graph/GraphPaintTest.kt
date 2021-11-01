package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GraphPaintTest {

    @Test
    fun getScaledTextPaint_withHalfScale_returnSmallerScaledPaint() {

        // act
        val scaledTextPaint = GraphPaint.getScaledTextPaint(0.5f)

        // assert
        assertThat(scaledTextPaint.textSize).isEqualTo(36F)
    }

    @Test
    fun getScaledTextPaint_withScale1_returnUnscaledPaint() {

        // act
        val scaledTextPaint = GraphPaint.getScaledTextPaint(1f)

        // assert
        assertThat(scaledTextPaint.textSize).isEqualTo(72F)
    }

    @Test
    fun getScaledTextPaint_withScale2_returnLargerScaledTextPaint() {

        // act
        val scaledTextPaint = GraphPaint.getScaledTextPaint(2f)

        // assert
        assertThat(scaledTextPaint.textSize).isEqualTo(144F)
    }

}