package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.view.View
import androidx.core.content.ContextCompat

class ResourceHelper {

    fun getColor(view: View, resourceId: Int) = ContextCompat.getColor(view.context, resourceId)

}
