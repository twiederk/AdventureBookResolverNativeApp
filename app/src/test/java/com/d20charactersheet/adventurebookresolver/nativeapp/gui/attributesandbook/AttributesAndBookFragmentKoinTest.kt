package com.d20charactersheet.adventurebookresolver.nativeapp.gui.attributesandbook

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

internal class AttributesAndBookFragmentKoinTest : KoinTest {

    private val attributesPanel: AttributesPanel by inject()
    private val bookPanel: BookPanel by inject()

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }
        declareMock<AttributesPanel>()
        declareMock<BookPanel>()
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
            on { inflate(R.layout.fragment_attributes_and_book, container, false) } doReturn rootView
        }
        val savedInstanceState = mock<Bundle>()

        // Act
        AttributesAndBookFragment()
            .onCreateView(inflater, container, savedInstanceState)

        // Assert
        verify(inflater).inflate(R.layout.fragment_attributes_and_book, container, false)
        verify(attributesPanel).create(any())
        verify(bookPanel).create(any())
    }

    @Test
    fun onResume() {
        // Arrange

        // Act
        AttributesAndBookFragment()
            .onResume()

        // Assert
        verify(attributesPanel).update()
        verify(bookPanel).update()
    }

}