package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GraphViewModel : ViewModel() {

    var scale by mutableStateOf(1F)
        private set

    fun onScaleChange(scale: Float) {
        this.scale = scale
    }

}
