package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.view.View
import android.widget.Toast

class MessageDisplay {

    fun display(view: View, message: String) {
        Toast.makeText(view.context, message, Toast.LENGTH_SHORT).show()
    }

}
