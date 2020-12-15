package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory

import android.view.View
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class GoldOnClickListener : View.OnClickListener, KoinComponent {
    protected val game: Game by inject()
    protected val goldPanel: GoldPanel by inject()
}

class GoldIncreaseOnClickListener : GoldOnClickListener() {

    override fun onClick(v: View?) {
        game.increaseGold()
        goldPanel.update()
    }

}

class GoldDecreaseOnClickListener : GoldOnClickListener() {

    override fun onClick(v: View?) {
        game.decreaseGold()
        goldPanel.update()
    }

}

