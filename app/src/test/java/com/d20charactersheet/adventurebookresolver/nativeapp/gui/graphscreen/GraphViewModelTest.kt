package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class GraphViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun onSearchQueryChange() {
        // arrange
        val graphViewModel = GraphViewModel()

        // act
        graphViewModel.onScaleChange(2F)

        // assert
        assertThat(graphViewModel.scale).isEqualTo(2F)
    }

}