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

    @Test
    fun getScaledEdgePaint_withHalfScale2_returnSmallerScaledEdgePaint() {

        // act
        val scaledEdgePaint = GraphPaint.getScaledEdgePaint(0.5F)

        // assert
        assertThat(scaledEdgePaint.strokeWidth).isEqualTo(5F)
    }

    @Test
    fun getScaledEdgePaint_withScale1_returnUnscaledEdgePaint() {

        // act
        val scaledEdgePaint = GraphPaint.getScaledEdgePaint(1F)

        // assert
        assertThat(scaledEdgePaint.strokeWidth).isEqualTo(10F)
    }

    @Test
    fun getScaledEdgePaint_withScale2_returnLargerScaledEdgePaint() {

        // act
        val scaledEdgePaint = GraphPaint.getScaledEdgePaint(2F)

        // assert
        assertThat(scaledEdgePaint.strokeWidth).isEqualTo(20F)
    }

}