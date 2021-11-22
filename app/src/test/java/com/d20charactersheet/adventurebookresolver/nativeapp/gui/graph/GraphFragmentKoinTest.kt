package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.kotlin.*

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
        val addActionFAB: FloatingActionButton = mock()
        val saveFAB: FloatingActionButton = mock()
        whenever(rootView.findViewById<FloatingActionButton>(R.id.action_add_floating_action_button)).doReturn(addActionFAB)
        whenever(rootView.findViewById<FloatingActionButton>(R.id.save_floating_action_button)).doReturn(saveFAB)
        val inflater: LayoutInflater = mock()
        whenever(inflater.inflate(R.layout.fragment_graph, container, false)).doReturn(rootView)

        // Act
        GraphFragment().onCreateView(inflater, container, mock())

        // Assert
        verify(inflater).inflate(R.layout.fragment_graph, container, false)
        verify(addActionFAB).setOnClickListener(any())
        verify(saveFAB).setOnClickListener(any())
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