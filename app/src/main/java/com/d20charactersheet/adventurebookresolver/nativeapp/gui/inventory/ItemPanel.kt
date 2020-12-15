package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory

import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.Panel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ItemPanel : Panel {

    internal lateinit var itemLabelEditText: EditText
    private lateinit var itemAddButton: Button
    internal lateinit var itemRecyclerView: RecyclerView
    internal var itemTouchHelper: ItemTouchHelper? = null

    override fun create(rootView: View) {
        itemLabelEditText = rootView.findViewById(R.id.item_label_edit_text)
        createItemAddButton(rootView)
        createActionMoveRecyclerView(rootView)
    }

    private fun createItemAddButton(rootView: View) {
        itemAddButton = rootView.findViewById(R.id.item_add_button)
        itemAddButton.setOnClickListener(ItemAddOnClickListener())
    }

    private fun createActionMoveRecyclerView(rootView: View) {
        val itemAdapter =
            ItemAdapter()
        itemRecyclerView = rootView.findViewById<RecyclerView>(R.id.item_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(rootView.context)
            adapter = itemAdapter
        }
        if (itemTouchHelper == null) {
            itemTouchHelper = ItemTouchHelper(
                ItemDeleteOnSwipeListener(
                    itemAdapter
                )
            )
        }
        itemTouchHelper?.attachToRecyclerView(itemRecyclerView)

    }


    override fun update() {
        itemRecyclerView.adapter?.notifyDataSetChanged()
    }

    fun getItem() = itemLabelEditText.text.toString()

    fun clear() = itemLabelEditText.setText("")

}


class ItemAddOnClickListener : View.OnClickListener, KoinComponent {

    private val game: Game by inject()
    private val itemPanel: ItemPanel by inject()

    override fun onClick(v: View?) {
        val item = itemPanel.getItem()
        if (item.isNotEmpty()) {
            game.addItemToInventory(item)
            itemPanel.clear()
            itemPanel.update()
        }
    }

}

