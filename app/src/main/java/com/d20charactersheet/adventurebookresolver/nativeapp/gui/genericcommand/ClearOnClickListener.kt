package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import android.view.View
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ClearOnClickListener : View.OnClickListener, KoinComponent {

    private val genericCommandPanel: GenericCommandPanel by inject()

    override fun onClick(v: View?) {
        genericCommandPanel.clearOutput()
    }

}
