package com.d20charactersheet.adventurebookresolver.nativeapp

import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

class InventoryPanelKoinTest : KoinTest {

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun create_inventory_panel() {

        // Arrange
        val itemAddButton: Button = mock()
        val itemRecyclerView: RecyclerView = mock()
        val rootView: View = mock {
            on { findViewById<EditText>(R.id.item_label_edit_text) } doReturn mock()
            on { findViewById<Button>(R.id.item_add_button) } doReturn itemAddButton
            on { findViewById<RecyclerView>(R.id.item_recycler_view) } doReturn itemRecyclerView
        }
        val underTest = InventoryPanel()
        underTest.itemTouchHelper = mock()


        // Act
        underTest.create(rootView)

        // Assert
        assertItemAddButton(itemAddButton)
        assertItemRecyclerView(itemRecyclerView)
        verify(underTest.itemTouchHelper)?.attachToRecyclerView(itemRecyclerView)
    }

    private fun assertItemAddButton(itemAddButton: Button) {
        argumentCaptor<ItemAddOnClickListener> {
            verify(itemAddButton).setOnClickListener(capture())
            assertThat(firstValue).isInstanceOf(ItemAddOnClickListener::class.java)
        }
    }

    private fun assertItemRecyclerView(itemRecyclerView: RecyclerView) {
        verify(itemRecyclerView).setHasFixedSize(true)
        argumentCaptor<LinearLayoutManager> {
            verify(itemRecyclerView).layoutManager = capture()
            assertThat(firstValue).isInstanceOf(LinearLayoutManager::class.java)
        }
        argumentCaptor<InventoryAdapter> {
            verify(itemRecyclerView).adapter = capture()
            assertThat(firstValue).isInstanceOf(InventoryAdapter::class.java)
        }
    }

    @Test
    fun getItem() {
        // Arrange
        val underTest = InventoryPanel()
        val editable = mock<Editable> {
            on { toString() } doReturn "myItem"
        }
        underTest.itemLabelEditText = mock {
            on { text } doReturn editable
        }

        // Act
        val item = underTest.getItem()

        // Assert
        assertThat(item).isEqualTo("myItem")
    }

    @Test
    fun clear() {
        // Arrange
        val underTest = InventoryPanel()
        underTest.itemLabelEditText = mock()

        // Act
        underTest.clear()

        // Assert
        verify(underTest.itemLabelEditText).setText("")
    }

    @Test
    fun update() {
        // Arrange
        val underTest = InventoryPanel()
        val inventoryAdapter: InventoryAdapter = mock()
        val itemRecyclerView: RecyclerView = mock {
            on { adapter } doReturn inventoryAdapter
        }
        underTest.inventoryRecyclerView = itemRecyclerView

        // Act
        underTest.update()

        // Assert
        verify(inventoryAdapter).notifyDataSetChanged()
    }
}

