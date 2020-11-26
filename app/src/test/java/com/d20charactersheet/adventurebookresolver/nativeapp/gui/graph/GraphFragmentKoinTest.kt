package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nhaarman.mockitokotlin2.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock

internal class GraphFragmentKoinTest : KoinTest {

    private val graphPanel: GraphPanel by inject()

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }
        declareMock<GraphPanel>()
    }


    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun onCreateView() {
        // Arrange
        val container: ViewGroup = mock()
        val rootView: View = mock()
        val fab: FloatingActionButton = mock()
        whenever(rootView.findViewById<FloatingActionButton>(R.id.action_add_floating_action_button)).doReturn(fab)
        val inflater: LayoutInflater = mock()
        whenever(inflater.inflate(R.layout.fragment_graph, container, false)).doReturn(rootView)

        // Act
        GraphFragment().onCreateView(inflater, container, mock())

        // Assert
        verify(inflater).inflate(R.layout.fragment_graph, container, false)
        verify(fab).setOnClickListener(any())
        verify(graphPanel).create(any())
    }

    @Test
    fun onResume() {

        // Act
        GraphFragment().onResume()

        // Assert
        verify(graphPanel).update()
    }

}