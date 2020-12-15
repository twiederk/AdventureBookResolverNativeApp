package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.appModule
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class InventoryFragmentKoinTest : KoinTest {

    private val goldPanel: GoldPanel by inject()
    private val provisionsPanel: ProvisionsPanel by inject()
    private val itemPanel: ItemPanel by inject()

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }
        declareMock<GoldPanel>()
        declareMock<ProvisionsPanel>()
        declareMock<ItemPanel>()
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

        val inflater = mock<LayoutInflater> {
            on { inflate(R.layout.fragment_inventory, container, false) } doReturn rootView
        }
        val savedInstanceState = mock<Bundle>()

        // Act
        InventoryFragment()
            .onCreateView(inflater, container, savedInstanceState)

        // Assert
        verify(inflater).inflate(R.layout.fragment_inventory, container, false)
        verify(goldPanel).create(any())
        verify(provisionsPanel).create(any())
        verify(itemPanel).create(any())
    }

    @Test
    fun onResume() {
        // Act
        InventoryFragment().onResume()

        // Assert
        verify(goldPanel).update()
        verify(provisionsPanel).update()
        verify(itemPanel).update()
    }

}