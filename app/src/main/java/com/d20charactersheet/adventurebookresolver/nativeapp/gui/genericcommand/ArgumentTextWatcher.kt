package com.d20charactersheet.adventurebookresolver.nativeapp.gui.genericcommand

import android.text.Editable
import android.text.TextWatcher
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ArgumentTextWatcher : TextWatcher, KoinComponent {

    private val genericCommandViewModel: GenericCommandViewModel by inject()

    override fun afterTextChanged(s: Editable) {}

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        genericCommandViewModel.onArgumentChange(s.toString())
    }

}
