package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GraphViewModel : ViewModel() {

    private val _scale = MutableLiveData(1F)
    val scale: LiveData<Float> = _scale

    fun onScaleChange(scale: Float) {
        _scale.value = scale
    }

}
